package com.github.ringoame196_s_mcPlugin.command

import com.github.ringoame196_s_mcPlugin.const.MessageConst
import com.github.ringoame196_s_mcPlugin.service.LockBlockService
import com.github.ringoame196_s_mcPlugin.service.PasswordService
import com.github.ringoame196_s_mcPlugin.service.PermissionService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class UnLockCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = CommonCommand.getPlayer(sender) ?: return true
        val block = CommonCommand.getTargetLockBlock(player) ?: return true
        val lockLocation = LockBlockService.getLockLocation(block) ?: return true

        if (!PasswordService.exists(lockLocation)) {
            player.sendMessage(MessageConst.NO_LOCK_MESSAGE)
            return true
        }

        if (PasswordService.authenticateOwner(lockLocation, player.uniqueId) || PermissionService.isAdmin(player)) {
            PasswordService.removeLockData(lockLocation)
            PasswordService.removeDB(lockLocation)
            player.sendMessage(MessageConst.UNLOCK_MESSAGE)
        } else {
            player.sendMessage(MessageConst.NO_PERMISSION_MESSAGE)
        }

        return true
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return null
    }
}
