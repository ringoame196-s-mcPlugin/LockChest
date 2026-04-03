package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.InputType
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class LockCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = CommonCommand.getPlayer(sender) ?: return true
        val block = CommonCommand.getTargetLockBlock(player) ?: return true

        CommonCommand.openInput(player, InputType.LOCK, block)
        return true
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return null
    }
}
