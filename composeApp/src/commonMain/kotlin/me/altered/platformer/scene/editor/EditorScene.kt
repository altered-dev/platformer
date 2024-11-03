package me.altered.platformer.scene.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
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

    fun pan(offset: Offset) {
        world.position += offset
    }

    fun zoom(delta: Offset, cursorPos: Offset, size: Size) {
        if (delta.y == 0.0f) return
        val cursorDistance = cursorPos - (world.position + size.center)
        val scaleFrom = world.scale
        when {
            delta.y > 0.0f -> world.scale /= 1.0f + delta.y * 0.1f
            delta.y < 0.0f -> world.scale *= 1.0f - delta.y * 0.1f
        }
        world.position += cursorDistance * (1 - world.scale / scaleFrom)
    }

    fun hover(cursorPos: Offset, size: Size): ObjectNode<*>? {
        val cursorDistance = screenToWorld(cursorPos, size)
        return world.objects.findLast { cursorDistance in it.globalBounds }
    }

    fun place(obj: ObjectNode<*>) {

    }

    fun screenToWorld(vec: Offset, size: Size) = world.screenToWorld(vec, size)

    fun worldToScreen(vec: Offset, size: Size) = world.worldToScreen(vec, size)
}
