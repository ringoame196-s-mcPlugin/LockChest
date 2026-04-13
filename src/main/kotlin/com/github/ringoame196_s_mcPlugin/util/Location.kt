package com.github.ringoame196_s_mcPlugin.util

import com.github.ringoame196_s_mcPlugin.data.LockLocation
import org.bukkit.Location

fun Location.toLockLocation(): LockLocation {
    return LockLocation(world.name, blockX, blockY, blockZ)
}
