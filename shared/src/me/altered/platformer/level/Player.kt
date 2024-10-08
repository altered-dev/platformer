package me.altered.platformer.level

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.graphics.Paint
import me.altered.platformer.engine.input.InputEvent
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.graphics.drawCircle
import me.altered.platformer.engine.graphics.drawRect
import me.altered.platformer.engine.graphics.translate
import kotlin.math.abs
import kotlin.math.sign
import kotlin.math.withSign

class Player(
    parent: Node? = null,
    position: Vector2f = Vector2f(),
    rotation: Float = 0f,
) : Node2D("player", parent, position, rotation) {

    private val acceleration = Vector2f(0.0f, Gravity)
    private val velocity = Vector2f()
    private val input = Vector2f()
    private val radius = 0.5f
    private val rect = Rect(-radius, -radius, radius, radius)

    private val paint = Paint {
        color = Color.Black
    }

    private val world: World
        get() = parent as World

    private val lastCollisions = mutableListOf<Vector2fc>()

    override fun physicsUpdate(delta: Float) {
        // apply acceleration/deceleration based on player input
        if (abs(velocity.x) < Epsilon) {
            velocity.x = 0.0f
        }
        acceleration.x = when {
            input.x == 0.0f -> -Deceleration.copySign(velocity.x)
            velocity.x.sign != input.x.sign || abs(velocity.x) <= WalkSpeed -> input.x * Acceleration
            else -> 0.0f
        }
        velocity += acceleration * delta
        velocity.set(
            x = velocity.x.coerceIn(-MaxSpeed, MaxSpeed),
            y = velocity.y.coerceIn(-MaxSpeed, MaxSpeed),
        )
        // apply velocity
        position += velocity * delta

        lastCollisions.clear()
        // handle collisions
        var isOnFloor = false
        world.collide(position, radius) { col ->
            lastCollisions += col
            // TODO: better floor/wall detection
            if (col.y - position.y > radius * 0.25f) isOnFloor = true
            val mv = position - col
            val norm = mv.normalize(radius - mv.length)
            if (!norm.isNaN()) position += norm
        }

        // stop the player from falling through the floor
        // also jump
        if (isOnFloor) {
            velocity.y = input.y * JumpForce
        }
    }

    private val colPaint = Paint { color = Color.Yellow }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(rect, paint)
        canvas.translate(-position)
        lastCollisions.forEach { col ->
            canvas.drawCircle(col.x, col.y, 0.05f, colPaint)
        }
    }

    override fun debugDraw(canvas: Canvas) = Unit

    override fun input(event: InputEvent) {
        when {
            event pressed Key.A -> input.x -= 1
            event released Key.A -> input.x += 1
            event pressed Key.D -> input.x += 1
            event released Key.D -> input.x -= 1
            event pressed Key.Space -> input.y -= 1
            event released Key.Space -> input.y += 1
        }
    }

    companion object {

        private fun Float.copySign(num: Float): Float {
            return if (abs(num) < Epsilon) 0.0f else withSign(num)
        }

        private const val UNIT = 2.5f

        private const val Gravity = 36.0f * UNIT

        private const val Epsilon = 0.05f * UNIT
        private const val WalkSpeed = 5.0f * UNIT
        private const val JumpForce = 10.0f * UNIT

        private const val MaxSpeed = 12.0f * UNIT
        private const val Acceleration = 25.0f * UNIT
        private const val Deceleration = 20.0f * UNIT
    }
}
