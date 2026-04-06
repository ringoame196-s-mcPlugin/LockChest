package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.LockBlockManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class OpenCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = CommonCommand.getPlayer(sender) ?: return true
        val block = CommonCommand.getTargetLockBlock(player) ?: return true

        LockBlockManager.openInventory(player, block)
        return true
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return null
    }
}
