package com.github.ringoame196_s_mcPlugin.input

import org.bukkit.block.Block
import java.util.UUID

data class InputData(
    val lockBlock: Block,
    val type: InputType,
    val owner: UUID,
)
