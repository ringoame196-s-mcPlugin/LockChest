package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.command.LockCommand
import com.github.ringoame196_s_mcPlugin.command.OpenCommand
import com.github.ringoame196_s_mcPlugin.command.UnLockCommand
import com.github.ringoame196_s_mcPlugin.data.db.DataBaseManager
import com.github.ringoame196_s_mcPlugin.event.Events
import com.github.ringoame196_s_mcPlugin.service.PasswordService
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this
    lateinit var db: DataBaseManager

    override fun onEnable() {
        super.onEnable()

        // DB
        db = DataBaseManager(this, "data.db")
        db.init()
        PasswordService.init(db)
        PasswordService.loadDB()

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
