package com.github.ringoame196_s_mcPlugin.event

import com.github.ringoame196_s_mcPlugin.const.FeedbackConst
import com.github.ringoame196_s_mcPlugin.data.model.LockData
import com.github.ringoame196_s_mcPlugin.data.model.LockLocation
import com.github.ringoame196_s_mcPlugin.input.InputAnvilInvManager
import com.github.ringoame196_s_mcPlugin.input.InputData
import com.github.ringoame196_s_mcPlugin.input.InputType
import com.github.ringoame196_s_mcPlugin.service.LockBlockService
import com.github.ringoame196_s_mcPlugin.service.PasswordService
import com.github.ringoame196_s_mcPlugin.util.HashManager
import org.bukkit.Bukkit
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
        val lockLocation = LockBlockService.getLockLocation(inputData.lockBlock) ?: return

        val inputPassWord = InputAnvilInvManager.getText(inv) ?: return

        when (inputData.type) {
            InputType.LOCK -> lock(lockLocation, inputPassWord, player)
            InputType.OPEN -> open(player, inputData, inputPassWord)
        }
    }

    private fun lock(lockLocation: LockLocation, inputPassWord: String, player: Player) {
        if (PasswordService.exists(lockLocation)) {
            player.sendMessage(FeedbackConst.ALREADY_LOCK_MESSAGE)
            return
        }

        // 仮登録
        PasswordService.addLockData(lockLocation, LockData("LOCKING", player.uniqueId))

        Bukkit.getScheduler().runTaskAsynchronously(
            plugin,
            Runnable {
                val hashPassWord = HashManager.hash(inputPassWord)

                Bukkit.getScheduler().runTask(
                    plugin,
                    Runnable {
                        val lockData = LockData(hashPassWord, player.uniqueId)
                        PasswordService.addLockData(lockLocation, lockData)
                        PasswordService.saveDB(lockLocation, lockData)

                        val sound = Sound.BLOCK_CHEST_LOCKED
                        player.sendMessage(FeedbackConst.LOCK.message)
                        player.playSound(player, FeedbackConst.LOCK.sound, 1f, 1f)
                    }
                )
            }
        )
    }

    private fun open(player: Player, inputData: InputData, inputPassword: String) {
        val lockBlock = inputData.lockBlock
        val lockLocation = LockBlockService.getLockLocation(lockBlock) ?: return

        Bukkit.getScheduler().runTaskAsynchronously(
            plugin,
            Runnable {
                val ok = PasswordService.authenticatePassWord(lockLocation, inputPassword)

                Bukkit.getScheduler().runTask(
                    plugin,
                    Runnable {
                        if (ok) {
                            LockBlockService.openInventory(player, lockBlock)
                        } else {
                            player.sendMessage(FeedbackConst.WRONG_PASSWORD.message)
                            player.playSound(player, FeedbackConst.WRONG_PASSWORD.sound, 1f, 1f)
                        }
                    }
                )
            }
        )
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val player = e.player
        val block = e.clickedBlock ?: return
        val action = e.action

        if (action != Action.RIGHT_CLICK_BLOCK) return

        val lockLocation = LockBlockService.getLockLocation(block) ?: return
        if (!PasswordService.exists(lockLocation)) {
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

        val lockLocation = LockBlockService.getLockLocation(block) ?: return
        if (PasswordService.exists(lockLocation)) {
            e.isCancelled = true
            player.sendMessage(FeedbackConst.NO_BREAK.message)
            player.playSound(player, FeedbackConst.NO_BREAK.sound, 1f, 1f)
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onExplode(e: EntityExplodeEvent) {
        e.blockList().removeIf { block ->
            LockBlockService.getLockLocation(block)?.let { PasswordService.exists(it) } == true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onBlockExplode(e: BlockExplodeEvent) {
        e.blockList().removeIf { block ->
            LockBlockService.getLockLocation(block)?.let { PasswordService.exists(it) } == true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onInventoryMoveItem(e: InventoryMoveItemEvent) {
        val source = e.source.holder as? BlockInventoryHolder?
        val destination = e.destination.holder

        val sourceLockLocation = LockBlockService.getLockLocation(source)
        val destinationLockLocation = LockBlockService.getLockLocation(destination)

        if (sourceLockLocation?.let { PasswordService.exists(it) } == true) {
            e.isCancelled = true
            return
        }

        if (destinationLockLocation?.let { PasswordService.exists(it) } == true) {
            e.isCancelled = true
        }
    }

    private fun checkInputAnvilInv(inv: Inventory): Boolean {
        return InputAnvilInvManager.isInputAnvilInv(inv)
    }
}
