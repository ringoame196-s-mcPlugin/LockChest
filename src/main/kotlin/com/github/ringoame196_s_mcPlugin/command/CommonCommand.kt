package com.github.ringoame196_s_mcPlugin.command

import com.github.ringoame196_s_mcPlugin.input.InputAnvilInvManager
import com.github.ringoame196_s_mcPlugin.input.InputType
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object CommonCommand {
    private val permitBlocks = listOf<Material>(
        Material.CHEST,
        Material.TRAPPED_CHEST,
        Material.BARREL,
        Material.FURNACE,
        Material.SMOKER,
        Material.BLAST_FURNACE
    )

    fun getPlayer(sender: CommandSender): Player? {
        if (sender !is Player) {
            sender.sendMessage("Only players can execute this command")
            return null
        }
        return sender
    }

    fun getTargetLockBlock(player: Player): Block? {
        val block = player.getTargetBlockExact(5)
        if (block == null) {
            player.sendMessage("${ChatColor.RED}Please select a block")
            return null
        } else if (!permitBlocks.contains(block.type) && !block.type.toString().contains("SHULKER_BOX")) {
            player.sendMessage("${ChatColor.RED}This block is excluded")
            return null
        }
        return block
    }

    fun openInput(player: Player, type: InputType, block: Block) {
        InputAnvilInvManager.openInv(player, type, block)
    }
}
