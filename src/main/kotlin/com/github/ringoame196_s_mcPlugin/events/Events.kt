package com.github.ringoame196_s_mcPlugin.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class Events : Listener {
    @EventHandler
    fun on(e: InventoryClickEvent) {
        val player = e.whoClicked
        val inv = e.clickedInventory ?: return
        val item = e.currentItem ?: return
        val slot = e.slot
    }
}
