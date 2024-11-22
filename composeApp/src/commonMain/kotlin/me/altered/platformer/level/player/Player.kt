package me.altered.platformer.level.player

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import me.altered.platformer.engine.geometry.normalize
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.Node2D
import me.altered.platformer.level.World
import kotlin.math.abs
import kotlin.math.sign
import kotlin.math.withSign

class Player(
    parent: Node? = null,
    position: Offset = Offset.Companion.Zero,
    rotation: Float = 0f,
) : Node2D("player", parent, position, rotation) {

    private var acceleration = Offset(0.0f, Gravity)
    private var velocity = Offset.Companion.Zero
    private var input = Offset.Companion.Zero
    private val radius = 0.5f
    private val rect = Rect(-radius, -radius, radius, radius)

    private val world: World
        get() = parent as World

    private val lastCollisions = mutableListOf<Offset>()

    override fun physicsUpdate(delta: Float) {
        // apply acceleration/deceleration based on player input
        if (abs(velocity.x) < Epsilon) {
            velocity = velocity.copy(x = 0.0f)
        }
        acceleration = acceleration.copy(
            x = when {
                input.x == 0.0f -> -Deceleration.copySign(velocity.x)
                velocity.x.sign != input.x.sign || abs(velocity.x) <= WalkSpeed -> input.x * Acceleration
                else -> 0.0f
            }
        )
        velocity += acceleration * delta
        velocity = Offset(
            x = velocity.x.coerceIn(-MaxSpeed, MaxSpeed),
            y = velocity.y.coerceIn(-MaxSpeed, MaxSpeed),
        )
        // apply velocity
        position += velocity * delta

        lastCollisions.clear()
        // handle collisions
        var isOnFloor = false
        world.collide(position, radius).forEach { (col, normal) ->
            lastCollisions += col
            // TODO: better floor/wall detection
            if (col.y - position.y > radius * 0.25f) isOnFloor = true
            val mv = position - col
            val norm = mv.normalize(radius - mv.getDistance())
            if (norm.isValid() && norm.isSpecified) position += norm
        }

        // stop the player from falling through the floor
        // also jump
        if (isOnFloor) {
            velocity = velocity.copy(y = input.y * JumpForce)
        }
    }

    override fun DrawScope.draw() {
        drawRect(Color.Companion.Black, rect.topLeft, rect.size)
        translate(-position.x, -position.y) {
            lastCollisions.forEach { col ->
                drawCircle(Color.Companion.Yellow, 0.05f, col)
            }
        }
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        return when (event.type) {
            KeyEventType.Companion.KeyDown -> when (event.key) {
                Key.Companion.A -> { input = input.copy(x = input.x - 1); true }
                Key.Companion.D -> { input = input.copy(x = input.x + 1); true }
                Key.Companion.Spacebar -> { input = input.copy(y = input.y - 1); true }
                else -> false
            }
            KeyEventType.Companion.KeyUp -> when (event.key) {
                Key.Companion.A -> { input = input.copy(x = input.x + 1); true }
                Key.Companion.D -> { input = input.copy(x = input.x - 1); true }
                Key.Companion.Spacebar -> { input = input.copy(y = input.y + 1); true }
                else -> false
            }
            else -> false
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
