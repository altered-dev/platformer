package me.altered.platformer.player

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.koml.sum
import me.altered.platformer.MainScene
import me.altered.platformer.engine.input.InputEvent
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.Paint
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
    private val radius = 10.0f
    private val rect = Rect(-radius, -radius, radius, radius)

    private val paint = Paint {
        color4f = Colors.Black
    }

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

        // handle collisions
        val collisions = (parent?.parent as MainScene).makeCollisions(position, radius)
        val dir = collisions.mapNotNull { col ->
            val mv = position - col
            mv.normalize(radius - mv.length).takeUnless { it.isNaN() }
        }.sum()
        position += dir
        // stop the player from falling through the floor
        // also jump
        if (isOnFloor(collisions)) {
            velocity.y = input.y * JumpForce
        }
    }

    private fun isOnFloor(collisions: List<Vector2fc>): Boolean {
        return collisions.any { it.y > position.y }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(rect, paint)
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

        private const val UNIT = 50.0f

        private const val Gravity = 36.0f * UNIT

        private const val Epsilon = 0.05f * UNIT
        private const val WalkSpeed = 5.0f * UNIT
        private const val JumpForce = 10.0f * UNIT

        private const val MaxSpeed = 12.0f * UNIT
        private const val Acceleration = 25.0f * UNIT
        private const val Deceleration = 20.0f * UNIT
    }
}
