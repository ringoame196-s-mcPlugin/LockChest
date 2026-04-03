package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.InputAnvilInvManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory

class Events : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val inv = e.inventory
        val player = e.whoClicked as? Player ?: return
        val slot = e.rawSlot
        if (!checkInputAnvilInv(inv)) {
            return
        }
        e.isCancelled = true

        if (slot != 2) {
            return
        }
        val text = InputAnvilInvManager.getText(inv) ?: return
        player.closeInventory()
        player.sendMessage(text)
        InputAnvilInvManager.deleteList(inv)
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        val inv = e.inventory
        if (!checkInputAnvilInv(inv)) {
            return
        }
        inv.clear()
    }

    private fun checkInputAnvilInv(inv: Inventory): Boolean {
        return InputAnvilInvManager.isInputAnvilInv(inv)
    }
}
