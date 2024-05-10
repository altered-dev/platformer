package me.altered.platformer.player

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect
import me.altered.platformer.glfw.input.InputEvent
import me.altered.platformer.glfw.input.Key
import me.altered.platformer.glfw.input.pressed
import me.altered.platformer.glfw.input.released
import me.altered.platformer.math.plusAssign
import me.altered.platformer.math.times
import me.altered.platformer.skija.translate
import me.altered.platformer.node.Node
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.buildPaint
import org.joml.Vector2f
import kotlin.math.sign

class Player(
    val position: Vector2f = Vector2f(),
    var rotation: Float = 0f,
) : Node() {

    override val name = "player"

    private val direction = Vector2f()
    private val gravity = 9.8f

    private val paint = buildPaint {
        color4f = Color.black
    }

    override fun physicsUpdate(delta: Float) {
        direction.y += gravity * delta
        position +=  direction * (200.0f * delta)
        rotation += direction.x.sign * 180.0f * delta
        if (position.y > 500.0f) {
            direction.y = 0.0f
            position.y = 500.0f
            rotation = 0.0f
        }
    }

    override fun draw(canvas: Canvas) {
        val rect = Rect.makeLTRB(-10f, -10f, 10f, 10f)
        canvas.apply {
            translate(position)
            rotate(rotation)
            drawRect(rect, paint)
        }
    }

    override fun input(event: InputEvent): Boolean {
        when {
            event pressed Key.A -> direction.x--
            event released Key.A -> direction.x++
            event pressed Key.D -> direction.x++
            event released Key.D -> direction.x--
            event pressed Key.SPACE -> direction.y = -3.0f
        }
        return false
    }
}
