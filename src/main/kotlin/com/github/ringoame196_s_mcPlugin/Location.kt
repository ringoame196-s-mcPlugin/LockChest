package com.github.ringoame196_s_mcPlugin

import org.bukkit.Location

fun Location.toLockLocation(): LockLocation {
    return LockLocation(world.name, blockX, blockY, blockZ)
}
