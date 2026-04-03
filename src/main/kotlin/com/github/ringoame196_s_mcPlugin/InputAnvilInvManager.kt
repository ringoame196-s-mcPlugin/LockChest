package com.github.ringoame196_s_mcPlugin

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.AnvilInventory
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object InputAnvilInvManager {
    private val inputAnvilInventorys = mutableMapOf<Inventory, InputData>()

    fun openInv(player: Player, type: InputType, blockLocation: Location) {
        player.openAnvil(null, true)
        val inv = player.openInventory.topInventory
        val data = InputData(blockLocation, type)
        inputAnvilInventorys[inv] = data
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
        return inputAnvilInventorys.keys.contains(inv)
    }

    fun deleteList(inv: Inventory) {
        inputAnvilInventorys.remove(inv)
    }

    fun getText(inv: Inventory): String? {
        val anvil = inv as? AnvilInventory ?: return null
        return anvil.renameText
    }
}
