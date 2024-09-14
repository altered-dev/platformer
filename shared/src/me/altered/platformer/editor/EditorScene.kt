package me.altered.platformer.editor

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.platformer.engine.graphics.contains
import me.altered.platformer.engine.graphics.drawCircle
import me.altered.platformer.engine.graphics.offset
import me.altered.platformer.engine.graphics.transform
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Middle
import me.altered.platformer.engine.input.Modifier
import me.altered.platformer.engine.input.MouseButton
import me.altered.platformer.engine.input.cursorMoved
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.input.scrolledWith
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.ui.Text
import me.altered.platformer.engine.util.logged
import me.altered.platformer.level.World
import me.altered.platformer.level.node.ObjectNode
import org.jetbrains.skia.Canvas

class EditorScene : Node2D("editor") {

    private var timeDirection = 0.0f
    private val mousePos = Vector2f()
    private var leftDragging = false
    private var middleDragging = false
    private var lastStart: Vector2f? = null

    private val world = +World()
    private val grid = world + Grid()

    private var hovered: ObjectNode<*>? by logged(null)
    private var selected: ObjectNode<*>? by logged(null)

    private val offsetText = +Text("offset: ")

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
                offsetText.text = "offset: ${world.position} size: ${world.size}"
            }
            event.cursorMoved() -> {
                if (middleDragging) {
                    world.position.x += event.x - mousePos.x
                    world.position.y += event.y - mousePos.y
                    offsetText.text = "offset: ${world.position} size: ${world.size}"
                }
                mousePos.set(event.x, event.y)
                hovered = world.objects.findLast { mousePos.screenToWorld() in it.bounds.offset(it.position) }
                middleDragging
            }
        }
    }

    override fun draw(canvas: Canvas) {
//        canvas.drawCircle(mousePos.x, mousePos.y, 2.0f, debugPaint)

        canvas.transform(world)
        val mousePos = mousePos.screenToWorld()
        canvas.drawCircle(mousePos.x, mousePos.y, 0.05f, debugPaint)
    }

    private fun Vector2fc.screenToWorld(): Vector2fc {
        return (this - world.offset) / world.size
    }

    private fun Vector2fc.worldToScreen(): Vector2fc {
        return this * world.size + world.offset
    }
}
