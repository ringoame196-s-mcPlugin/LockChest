package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.data.LockData
import com.github.ringoame196_s_mcPlugin.data.LockLocation
import com.github.ringoame196_s_mcPlugin.input.InputAnvilInvManager
import com.github.ringoame196_s_mcPlugin.input.InputData
import com.github.ringoame196_s_mcPlugin.input.InputType
import com.github.ringoame196_s_mcPlugin.service.LockBlockManager
import com.github.ringoame196_s_mcPlugin.service.PasswordManager
import com.github.ringoame196_s_mcPlugin.util.HashManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.BlockInventoryHolder
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.Plugin

class Events(private val plugin: Plugin) : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val inv = e.inventory
        val player = e.whoClicked as? Player ?: return
        val slot = e.rawSlot
        val inputData = InputAnvilInvManager.getInputData(inv)
        if (inputData == null) {
            return
        }
        e.isCancelled = true

        if (slot != 2) {
            return
        }

        player.closeInventory()
        InputAnvilInvManager.deleteList(inv)
        val lockLocation = LockBlockManager.getLockLocation(inputData.lockBlock) ?: return

        val inputPassWord = InputAnvilInvManager.getText(inv) ?: return

        when (inputData.type) {
            InputType.LOCK -> lock(lockLocation, inputPassWord, player)
            InputType.OPEN -> open(player, inputData, inputPassWord)
        }
    }

    private fun lock(lockLocation: LockLocation, inputPassWord: String, player: Player) {
        if (PasswordManager.exists(lockLocation)) {
            val message = "${ChatColor.RED}既にロックがかかっています"
            player.sendMessage(message)
            return
        }

        // 仮登録
        PasswordManager.addLockData(lockLocation, LockData("LOCKING", player.uniqueId))

        Bukkit.getScheduler().runTaskAsynchronously(
            plugin,
            Runnable {
                val hashPassWord = HashManager.hash(inputPassWord)

                Bukkit.getScheduler().runTask(
                    plugin,
                    Runnable {
                        val lockData = LockData(hashPassWord, player.uniqueId)
                        PasswordManager.addLockData(lockLocation, lockData)
                        PasswordManager.saveDB(lockLocation, lockData)

                        val message = "${ChatColor.YELLOW}ロックをかけました"
                        val sound = Sound.BLOCK_CHEST_LOCKED
                        player.sendMessage(message)
                        player.playSound(player, sound, 1f, 1f)
                    }
                )
            }
        )
    }

    private fun open(player: Player, inputData: InputData, inputPassword: String) {
        val lockBlock = inputData.lockBlock
        val lockLocation = LockBlockManager.getLockLocation(lockBlock) ?: return

        Bukkit.getScheduler().runTaskAsynchronously(
            plugin,
            Runnable {
                val ok = PasswordManager.authenticatePassWord(lockLocation, inputPassword)

                Bukkit.getScheduler().runTask(
                    plugin,
                    Runnable {
                        if (ok) {
                            LockBlockManager.openInventory(player, lockBlock)
                        } else {
                            sendFailure(player)
                        }
                    }
                )
            }
        )
    }

    private fun sendFailure(player: Player) {
        val message = "${ChatColor.RED}パスワードが間違っています"
        player.sendMessage(message)
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val player = e.player
        val block = e.clickedBlock ?: return
        val action = e.action

        if (action != Action.RIGHT_CLICK_BLOCK) return

        val lockLocation = LockBlockManager.getLockLocation(block) ?: return
        if (!PasswordManager.exists(lockLocation)) {
            return
        }
        e.isCancelled = true
        InputAnvilInvManager.openInv(player, InputType.OPEN, block)
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        val inv = e.inventory
        if (!checkInputAnvilInv(inv)) {
            return
        }
        inv.clear()
    }

    @EventHandler
    fun onBlockBreakBlock(e: BlockBreakEvent) {
        val block = e.block
        val player = e.player
        val message = "${ChatColor.RED}This block is locked"

        val lockLocation = LockBlockManager.getLockLocation(block) ?: return
        if (PasswordManager.exists(lockLocation)) {
            e.isCancelled = true
            player.sendMessage(message)
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onExplode(e: EntityExplodeEvent) {
        e.blockList().removeIf { block ->
            LockBlockManager.getLockLocation(block)?.let { PasswordManager.exists(it) } == true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onBlockExplode(e: BlockExplodeEvent) {
        e.blockList().removeIf { block ->
            LockBlockManager.getLockLocation(block)?.let { PasswordManager.exists(it) } == true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onInventoryMoveItem(e: InventoryMoveItemEvent) {
        val source = e.source.holder as? BlockInventoryHolder?
        val destination = e.destination.holder

        val sourceLockLocation = LockBlockManager.getLockLocation(source)
        val destinationLockLocation = LockBlockManager.getLockLocation(destination)

        if (sourceLockLocation?.let { PasswordManager.exists(it) } == true) {
            e.isCancelled = true
            return
        }

        if (destinationLockLocation?.let { PasswordManager.exists(it) } == true) {
            e.isCancelled = true
        }
    }

    private fun checkInputAnvilInv(inv: Inventory): Boolean {
        return InputAnvilInvManager.isInputAnvilInv(inv)
    }
}
