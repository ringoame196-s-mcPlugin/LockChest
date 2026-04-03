package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.InputAnvilInvManager
import com.github.ringoame196_s_mcPlugin.InputType
import org.bukkit.ChatColor
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object CommonCommand {

    fun getPlayer(sender: CommandSender): Player? {
        if (sender !is Player) {
            sender.sendMessage("Only players can execute this command")
            return null
        }
        return sender
    }

    fun getTargetBlock(player: Player): Block? {
        val block = player.getTargetBlockExact(5)
        if (block == null) {
            player.sendMessage("${ChatColor.RED}ブロックを指定してください")
            return null
        }
        return block
    }

    fun openInput(player: Player, type: InputType, block: Block) {
        InputAnvilInvManager.openInv(player, type, block.location)
    }
}
