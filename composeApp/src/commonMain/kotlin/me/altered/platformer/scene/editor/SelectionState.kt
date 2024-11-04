package me.altered.platformer.scene.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import me.altered.platformer.level.node.ObjectNode

class SelectionState {

    var rect by mutableStateOf<Rect?>(null)
    val selection = mutableStateListOf<ObjectNode<*>>()
    var hovered by mutableStateOf<ObjectNode<*>?>(null)

    val isSelecting: Boolean
        get() = rect != null

    fun selectSingle(node: ObjectNode<*>) {
        selection.clear()
        selection += node
    }

    fun selectAll(nodes: Collection<ObjectNode<*>>) {
        selection.clear()
        selection.addAll(nodes)
    }

    fun selectHovered() {
        hovered?.let { selectSingle(it) } ?: deselect()
    }

    fun deselect() {
        selection.clear()
    }
}

@Composable
fun rememberSelectionState() = remember { SelectionState() }
