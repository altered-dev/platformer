package me.altered.platformer.action.node

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

class EmptyNode(
    position: Offset = Offset.Zero,
) : ActionNode {

    override var position: Offset by mutableStateOf(position)
}
