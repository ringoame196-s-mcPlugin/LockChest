package com.github.ringoame196_s_mcPlugin

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object InputAnvilInventoryManager {
    private val inventoryList = mutableListOf<Inventory>()

    fun makeInventory(): Inventory {
        val inv = Bukkit.createInventory(null, InventoryType.ANVIL)
        val item = ItemStack(Material.PAPER)
        val meta = item.itemMeta
        meta?.setDisplayName("[パスワード入力]")
        item.itemMeta = meta
        inv.setItem(0, item)
        inventoryList.add(inv)
        return inv
    }

    fun isInputAnvilInventory(inventory: Inventory): Boolean {
        return inventoryList.contains(inventory)
    }
}
