package com.github.ringoame196_s_mcPlugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class LockCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            val message = "Only players can execute this command"
            sender.sendMessage(message)
            return true
        }

        return true
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return null
    }
}
