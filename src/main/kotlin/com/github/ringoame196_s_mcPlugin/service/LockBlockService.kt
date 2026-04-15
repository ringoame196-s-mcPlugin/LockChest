package com.github.ringoame196_s_mcPlugin.service

import com.github.ringoame196_s_mcPlugin.const.MessageConst
import com.github.ringoame196_s_mcPlugin.data.model.LockLocation
import com.github.ringoame196_s_mcPlugin.util.toLockLocation
import org.bukkit.block.Block
import org.bukkit.block.Chest
import org.bukkit.block.DoubleChest
import org.bukkit.entity.Player
import org.bukkit.inventory.BlockInventoryHolder
import org.bukkit.inventory.InventoryHolder

object LockBlockService {
    fun openInventory(player: Player, block: Block) {
        val state = block.state as? InventoryHolder
        val inv = state?.inventory
        if (inv == null) {
            player.sendMessage(MessageConst.NO_OPEN_MESSAGE)
        } else {
            player.openInventory(inv)
        }
    }

    fun getLockLocation(block: Block?): LockLocation? {
        block ?: return null
        val state = block.state

        if (state is Chest) {
            val invHolder = state.inventory.holder
            if (invHolder is DoubleChest) {
                val left = invHolder.leftSide as Chest
                return left.block.location.toLockLocation()
            }
        }

        return block.location.toLockLocation()
    }

    fun getLockLocation(holder: InventoryHolder?): LockLocation? {
        return when (holder) {
            is DoubleChest -> getLockLocation(holder.location.block)
            is BlockInventoryHolder -> getLockLocation(holder.block)
            else -> null
        }
    }
}
