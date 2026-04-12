package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.commands.LockCommand
import com.github.ringoame196_s_mcPlugin.commands.OpenCommand
import com.github.ringoame196_s_mcPlugin.commands.UnLockCommand
import com.github.ringoame196_s_mcPlugin.events.Events
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this
    lateinit var db: DataBaseManager

    override fun onEnable() {
        super.onEnable()

        // DB
        db = DataBaseManager(this, "data.db")
        db.init()
        PasswordManager.init(db)
        PasswordManager.loadDB()

        // Events
        server.pluginManager.registerEvents(Events(plugin), plugin)

        // Command
        val lockCommand = getCommand("lock")
        lockCommand!!.setExecutor(LockCommand())
        val unLockCommand = getCommand("unlock")
        unLockCommand!!.setExecutor(UnLockCommand())
        val openCommand = getCommand("open")
        openCommand!!.setExecutor(OpenCommand())
    }

    override fun onDisable() {
        super.onDisable()
        db.close()
    }
}
