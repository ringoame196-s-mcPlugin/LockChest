package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.commands.LockCommand
import com.github.ringoame196_s_mcPlugin.commands.UnLockCommand
import com.github.ringoame196_s_mcPlugin.events.Events
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this
    override fun onEnable() {
        super.onEnable()
        server.pluginManager.registerEvents(Events(), plugin)
        val lockCommand = getCommand("lock")
        lockCommand!!.setExecutor(LockCommand())
        val unLockCommand = getCommand("unlock")
        unLockCommand!!.setExecutor(UnLockCommand())
    }
}
