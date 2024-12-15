package me.altered.platformer.scene.editor.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import me.altered.platformer.level.node.MutableObjectNode
import kotlin.math.max
import kotlin.math.min

class SelectionState {

    var rect by mutableStateOf<Rect?>(null)
    val selection = mutableStateListOf<MutableObjectNode>()
    var hovered by mutableStateOf<MutableObjectNode?>(null)

    val isSelecting: Boolean
        get() = rect != null

    fun getSelectionRect(size: Size, worldToScreen: Offset.(Size) -> Offset): Rect? {
        if (selection.isEmpty()) return null
        var left = Float.POSITIVE_INFINITY
        var top = Float.POSITIVE_INFINITY
        var right = Float.NEGATIVE_INFINITY
        var bottom = Float.NEGATIVE_INFINITY
        selection.forEach { obj ->
            val bounds = obj.globalBounds
            left = min(left, bounds.left)
            top = min(top, bounds.top)
            right = max(right, bounds.right)
            bottom = max(bottom, bounds.bottom)
        }
        val topLeft = Offset(left, top).worldToScreen(size)
        val bottomRight = Offset(right, bottom).worldToScreen(size)
        return Rect(topLeft, bottomRight)
    }

    fun getSelectionRect(): Rect? {
        if (selection.isEmpty()) return null
        var left = Float.POSITIVE_INFINITY
        var top = Float.POSITIVE_INFINITY
        var right = Float.NEGATIVE_INFINITY
        var bottom = Float.NEGATIVE_INFINITY
        selection.forEach { obj ->
            val bounds = obj.globalBounds
            left = min(left, bounds.left)
            top = min(top, bounds.top)
            right = max(right, bounds.right)
            bottom = max(bottom, bounds.bottom)
        }
        val topLeft = Offset(left, top)
        val bottomRight = Offset(right, bottom)
        return Rect(topLeft, bottomRight)
    }

    fun selectSingle(obj: MutableObjectNode?) {
        selection.clear()
        if (obj == null) return
        selection += obj
    }

    fun selectAll(objs: Collection<MutableObjectNode>) {
        selection.clear()
        selection.addAll(objs)
    }

    fun selectHovered(): Boolean {
        hovered?.let { selectSingle(it) }
            ?: deselect().also { return false }
        return true
    }

    fun deselect() {
        if (hovered in selection) hovered = null
        selection.clear()
    }
}

@Composable
fun rememberSelectionState() = remember { SelectionState() }
