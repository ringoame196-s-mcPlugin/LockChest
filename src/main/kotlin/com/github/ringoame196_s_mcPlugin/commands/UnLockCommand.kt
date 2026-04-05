package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.PasswordManager
import com.github.ringoame196_s_mcPlugin.toLockLocation
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class UnLockCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = CommonCommand.getPlayer(sender) ?: return true
        val block = CommonCommand.getTargetLockBlock(player) ?: return true
        val location = block.location
        val lockLocation = location.toLockLocation()

        if (!PasswordManager.exists(lockLocation)) {
            val message = "${ChatColor.RED}ロックがかかっていません"
            player.sendMessage(message)
            return true
        }

        if (PasswordManager.authenticateOwner(lockLocation, player.uniqueId)) {
            PasswordManager.removeLockData(lockLocation)
            val message = "${ChatColor.YELLOW}ロックを解除しました"
            player.sendMessage(message)
        } else {
            val message = "${ChatColor.RED}あなたはロックを解除することはできません"
            player.sendMessage(message)
        }

        return true
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return null
    }
}
