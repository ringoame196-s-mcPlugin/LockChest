package com.github.ringoame196_s_mcPlugin

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.AnvilInventory
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object InputAnvilInvManager {
    private val inputAnvilInventoryList = mutableListOf<Inventory>()

    fun openInv(player: Player) {
        player.openAnvil(null, true)
        val inv = player.openInventory.topInventory
        inputAnvilInventoryList.add(inv)
        val paper = makeInputPaper()
        inv.setItem(0, paper)
    }

    private fun makeInputPaper(): ItemStack {
        val item = ItemStack(Material.PAPER)
        val meta = item.itemMeta
        meta.setDisplayName("パスワード:")
        meta.lore = listOf("パスワードを入力してください")
        item.setItemMeta(meta)
        return item
    }

    fun isInputAnvilInv(inv: Inventory): Boolean {
        return inputAnvilInventoryList.contains(inv)
    }

    fun deleteList(inv: Inventory) {
        inputAnvilInventoryList.remove(inv)
    }

    fun getText(inv: Inventory): String? {
        val anvil = inv as? AnvilInventory ?: return null
        return anvil.renameText
    }
}
