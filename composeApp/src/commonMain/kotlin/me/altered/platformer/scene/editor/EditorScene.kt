package me.altered.platformer.scene.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import me.altered.platformer.level.World
import me.altered.platformer.node.CanvasNode

class EditorScene : CanvasNode("editor") {

    private val world = +World()
    private val grid = world + Grid()

    fun move(offset: Offset) {
        world.position += offset
    }

    fun resize(delta: Float, cursorPos: Offset, size: Size) {
        if (delta == 0.0f) return
        val cursorDistance = cursorPos - (world.position + size.center)
        val scaleFrom = world.scale
        when {
            delta > 0.0f -> world.scale /= 1.0f + delta * 0.1f
            delta < 0.0f -> world.scale *= 1.0f - delta * 0.1f
        }
        world.position += cursorDistance * (1 - world.scale / scaleFrom)
    }
}
