package me.altered.platformer.player

import me.altered.koml.Vector2f
import me.altered.platformer.engine.input.InputEvent
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.paint
import kotlin.math.sign

class Player(
    parent: Node? = null,
    position: Vector2f = Vector2f(),
    rotation: Float = 0f,
) : Node2D("player", parent, position, rotation) {

    private val direction = Vector2f()
    private val gravity = 9.8f

    private val paint = paint {
        color4f = Colors.black
    }

    override fun update(delta: Float) {
        // might have been a mistake to move this from physics update
        position +=  direction * (200.0f * delta)
        rotation += direction.x.sign * 180.0f * delta
        if (position.y > 500.0f) {
            position.y = 500.0f
            rotation = 0.0f
        }
    }

    override fun physicsUpdate(delta: Float) {
        direction.y += gravity * delta
        if (position.y > 500.0f) {
            direction.y = 0.0f
        }
    }

    override fun draw(canvas: Canvas) {
        val rect = Rect.makeLTRB(-10f, -10f, 10f, 10f)
        canvas.drawRect(rect, paint)
    }

    override fun input(event: InputEvent) {
        when {
            event pressed Key.A -> direction.x--
            event released Key.A -> direction.x++
            event pressed Key.D -> direction.x++
            event released Key.D -> direction.x--
            event pressed Key.SPACE -> direction.y = -3.0f
        }
    }
}
