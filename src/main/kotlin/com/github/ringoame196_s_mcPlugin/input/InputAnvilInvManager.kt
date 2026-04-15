package com.github.ringoame196_s_mcPlugin.input

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.AnvilInventory
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object InputAnvilInvManager {
    private val inputAnvilInventorys = mutableMapOf<Inventory, InputData>()
    private const val DISPLAY_NAME = "パスワード:"
    private val LORE = listOf("パスワードを入力してください")

    fun openInv(player: Player, type: InputType, lockBlock: Block) {
        player.openAnvil(null, true)
        val inv = player.openInventory.topInventory
        val data = InputData(lockBlock, type, player.uniqueId)
        inputAnvilInventorys[inv] = data
        val paper = makeInputPaper()
        inv.setItem(0, paper)
    }

    private fun makeInputPaper(): ItemStack {
        val item = ItemStack(Material.PAPER)
        val meta = item.itemMeta
        meta.setDisplayName(DISPLAY_NAME)
        meta.lore = LORE
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

    fun getInputData(inv: Inventory): InputData? {
        return inputAnvilInventorys[inv]
    }
}
