package com.github.ringoame196_s_mcPlugin.service

import org.bukkit.entity.Player

object PermissionService {
    private const val ADMIN_PERMISSION_NAME = "lockchest.admin"

    fun isAdmin(player: Player): Boolean {
        return player.hasPermission(ADMIN_PERMISSION_NAME)
    }
}
