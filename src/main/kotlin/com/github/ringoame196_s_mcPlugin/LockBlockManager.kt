package com.github.ringoame196_s_mcPlugin

import org.bukkit.ChatColor
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryHolder

object LockBlockManager {
    fun openInventory(player: Player, block: Block) {
        val state = block.state as? InventoryHolder?
        val inv = state?.inventory
        if (inv == null) {
            val message = "${ChatColor.RED}This block does not have an inventory"
            player.sendMessage(message)
        } else {
            player.openInventory(inv)
        }
    }
}
