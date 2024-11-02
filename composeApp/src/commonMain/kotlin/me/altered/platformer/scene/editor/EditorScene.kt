package me.altered.platformer.scene.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import me.altered.platformer.geometry.scale
import me.altered.platformer.level.World
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.node.CanvasNode

class EditorScene(
    vararg objs: ObjectNode<*>,
) : CanvasNode("editor") {

    private val world = +World()
    private val grid = world + Grid()

    init {
        world.addChildren(objs.asIterable())
    }

    fun move(offset: Offset) {
        world.position += offset
    }

    fun resize(delta: Offset, cursorPos: Offset, size: Size) {
        if (delta.y == 0.0f) return
        val cursorDistance = cursorPos - (world.position + size.center)
        val scaleFrom = world.scale
        when {
            delta.y > 0.0f -> world.scale /= 1.0f + delta.y * 0.1f
            delta.y < 0.0f -> world.scale *= 1.0f - delta.y * 0.1f
        }
        world.position += cursorDistance * (1 - world.scale / scaleFrom)
    }

    fun hover(cursorPos: Offset, size: Size): Pair<ObjectNode<*>, Rect>? {
        val cursorDistance = screenToWorld(cursorPos, size)
        val obj = world.objects.findLast { cursorDistance in it.bounds.translate(it.globalPosition) }
            ?: return null
        return obj to world.worldToScreen(obj.bounds.translate(obj.globalPosition), size)
    }

    fun place(obj: ObjectNode<*>) {

    }

    fun screenToWorld(vec: Offset, size: Size) = world.screenToWorld(vec, size)

    fun worldToScreen(vec: Offset, size: Size) = world.worldToScreen(vec, size)
}
