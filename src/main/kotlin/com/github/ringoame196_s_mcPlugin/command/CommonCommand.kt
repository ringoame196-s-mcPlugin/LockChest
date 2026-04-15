package com.github.ringoame196_s_mcPlugin.command

import com.github.ringoame196_s_mcPlugin.const.MessageConst
import com.github.ringoame196_s_mcPlugin.input.InputAnvilInvManager
import com.github.ringoame196_s_mcPlugin.input.InputType
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
            sender.sendMessage(MessageConst.PLAYER_ONLY_MESSAGE)
            return null
        }
        return sender
    }

    fun getTargetLockBlock(player: Player): Block? {
        val block = player.getTargetBlockExact(5)
        if (block == null) {
            return null
        } else if (!permitBlocks.contains(block.type) && !block.type.toString().contains("SHULKER_BOX")) {
            player.sendMessage(MessageConst.UNSUPPORTED_BLOCK_MESSAGE)
            return null
        }
        return block
    }

    fun openInput(player: Player, type: InputType, block: Block) {
        InputAnvilInvManager.openInv(player, type, block)
    }
}
