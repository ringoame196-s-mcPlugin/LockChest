package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.InputAnvilInvManager
import com.github.ringoame196_s_mcPlugin.InputType
import com.github.ringoame196_s_mcPlugin.LockBlockManager
import com.github.ringoame196_s_mcPlugin.LockData
import com.github.ringoame196_s_mcPlugin.PasswordManager
import com.github.ringoame196_s_mcPlugin.toLockLocation
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.Inventory

class Events : Listener {
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

        val pass = InputAnvilInvManager.getText(inv) ?: return
        player.closeInventory()
        player.sendMessage(pass)
        InputAnvilInvManager.deleteList(inv)
        val lockData = LockData(pass, player.uniqueId)
        val lockLocation = inputData.lockBlock.location.toLockLocation()

        when (inputData.type) {
            InputType.LOCK -> PasswordManager.addLockData(lockLocation, lockData)
            InputType.UNLOCK -> PasswordManager.removeLockData(lockLocation)
            InputType.OPEN -> LockBlockManager.openInventory(player, inputData.lockBlock)
        }
    }

    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val player = e.player
        val block = e.clickedBlock ?: return
        if (!PasswordManager.exists(block.location.toLockLocation())) {
            return
        }
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

        if (PasswordManager.exists(block.location.toLockLocation())) {
            e.isCancelled = true
            player.sendMessage(message)
        }
    }

    private fun checkInputAnvilInv(inv: Inventory): Boolean {
        return InputAnvilInvManager.isInputAnvilInv(inv)
    }
}
