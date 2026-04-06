package com.github.ringoame196_s_mcPlugin

import org.bukkit.entity.Player

object PermissionManager {
    private const val ADMIN_PERMISSION_NAME = "lockchest.admin"

    fun isAdmin(player: Player): Boolean {
        return player.hasPermission(ADMIN_PERMISSION_NAME)
    }
}
