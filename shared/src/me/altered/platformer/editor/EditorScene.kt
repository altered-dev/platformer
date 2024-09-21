package me.altered.platformer.editor

import me.altered.koml.Vector2f
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Middle
import me.altered.platformer.engine.input.Modifier
import me.altered.platformer.engine.input.MouseButton
import me.altered.platformer.engine.input.cursorMoved
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.input.scrolledWith
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.level.World

class EditorScene : Node2D("editor") {

    private val mousePos = Vector2f()
    private var middleDragging = false

    private val world = +World()
    private val grid = world + Grid()

    private val topPanel = +TopPanel()

    override fun input(event: InputEvent) {
        when {
            // camera
            event pressed MouseButton.Middle -> middleDragging = true
            event released MouseButton.Middle -> middleDragging = false

            event scrolledWith Modifier.Control -> {
                val oldPos = mousePos.screenToWorld()
                when {
                    event.dy < 0 -> world.size *= 1.1f
                    event.dy > 0 -> world.size /= 1.1f
                }
                val newPos = mousePos.screenToWorld()
                world.position += (newPos - oldPos) * world.size
            }
            event.cursorMoved() -> {
                if (middleDragging) {
                    world.position.x += event.x - mousePos.x
                    world.position.y += event.y - mousePos.y
                }
                mousePos.set(event.x, event.y)
                middleDragging
            }
        }
    }

    companion object {

        private const val TAG = "EditorScene"
    }
}
