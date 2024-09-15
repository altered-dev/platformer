package me.altered.platformer.editor

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Middle
import me.altered.platformer.engine.input.Modifier
import me.altered.platformer.engine.input.MouseButton
import me.altered.platformer.engine.input.cursorMoved
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.input.scrolledWith
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.ui.Box
import me.altered.platformer.engine.ui.Column
import me.altered.platformer.engine.ui.Row
import me.altered.platformer.engine.ui.center
import me.altered.platformer.engine.ui.end
import me.altered.platformer.engine.ui.expand
import me.altered.platformer.engine.ui.padding
import me.altered.platformer.engine.ui.px
import me.altered.platformer.engine.ui.start
import me.altered.platformer.engine.ui.wrap
import me.altered.platformer.level.World
import org.jetbrains.skia.Shader

class EditorScene : Node2D("editor") {

    private val mousePos = Vector2f()
    private var middleDragging = false

    private val world = +World()
    private val grid = world + Grid()

    private val row = +Row(
        name = "test",
        width = 500.px,
        height = wrap(),
        padding = padding(all = 10.0f),
        horizontalAlignment = end,
        verticalAlignment = center,
        spacing = 10.0f,
        fill = Shader.makeColor(Color.Blue.value),
    )

    private val child1 = row + Box(
        name = "child1",
        width = 100.px,
        height = expand(),
        fill = Shader.makeColor(Color.Red.value),
    )

    private val column = row + Column(
        name = "column",
        width = 100.px,
        height = 500.px,
        padding = padding(all = 10.0f),
        horizontalAlignment = center,
        verticalAlignment = center,
        spacing = 50.0f,
        fill = Shader.makeColor(Color.Red.value),
    )

    private val child2 = column + Box(
        name = "child2",
        width = expand(),
        height = 200.px,
        fill = Shader.makeColor(Color.Green.value),
    )

    private val child3 = column + Box(
        name = "child3",
        width = expand(fraction = 0.2f),
        height = 100.px,
        fill = Shader.makeColor(Color.Green.value),
    )

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

    private fun Vector2fc.screenToWorld(): Vector2fc {
        return (this - world.offset) / world.size
    }

    private fun Vector2fc.worldToScreen(): Vector2fc {
        return this * world.size + world.offset
    }
}
