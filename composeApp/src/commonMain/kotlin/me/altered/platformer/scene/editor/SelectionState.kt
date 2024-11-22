package me.altered.platformer.scene.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.level.objects.MutableObject
import kotlin.math.max
import kotlin.math.min

class SelectionState {

    var rect by mutableStateOf<Rect?>(null)
    val selection = mutableStateListOf<ObjectNode<*>>()
    var hovered by mutableStateOf<ObjectNode<*>?>(null)

    val selection2 = mutableStateListOf<MutableObject>()
    var hovered2 by mutableStateOf<MutableObject?>(null)

    val isSelecting: Boolean
        get() = rect != null

    fun getSelectionRect(size: Size, worldToScreen: Offset.(Size) -> Offset): Rect? {
        if (selection2.isEmpty()) return null
        var left = Float.POSITIVE_INFINITY
        var top = Float.POSITIVE_INFINITY
        var right = Float.NEGATIVE_INFINITY
        var bottom = Float.NEGATIVE_INFINITY
        selection2.forEach { obj ->
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
        if (selection2.isEmpty()) return null
        var left = Float.POSITIVE_INFINITY
        var top = Float.POSITIVE_INFINITY
        var right = Float.NEGATIVE_INFINITY
        var bottom = Float.NEGATIVE_INFINITY
        selection2.forEach { obj ->
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

    fun selectSingle(obj: MutableObject?) {
        selection2.clear()
        if (obj == null) return
        selection2 += obj
    }

    fun selectAll(objs: Collection<MutableObject>) {
        selection2.clear()
        selection2.addAll(objs)
    }

    fun selectHovered(): Boolean {
        hovered2?.let { selectSingle(it) }
            ?: deselect().also { return false }
        return true
    }

    fun deselect() {
        selection2.clear()
    }
}

@Composable
fun rememberSelectionState() = remember { SelectionState() }
