package me.altered.platformer.scene.main

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.expression.Easing
import me.altered.platformer.expression.animated
import me.altered.platformer.expression.at
import me.altered.platformer.expression.const
import me.altered.platformer.expression.with
import me.altered.platformer.level.Player
import me.altered.platformer.level.data.linear
import me.altered.platformer.level.data.solid
import me.altered.platformer.level.rectangle
import me.altered.platformer.level.world
import me.altered.platformer.node.CanvasNode

class MainScene(
    private val textMeasurer: TextMeasurer,
) : CanvasNode("main") {

    private var timeDirection = 0.0f
    private var fps = 0.0f

    private val world = +world(
        position = Offset.Zero,
    ) {
        rectangle(
            name = "floor",
            x = const(0.0f),
            y = const(5.0f),
            width = const(50.0f),
            height = const(1.0f),
            fill = const(solid(0x80000000)),
        )
        rectangle(
            name = "box",
            x = animated(
                -6.0f at 0.0f,
                10.0f at 2.0f with Easing.BackOut,
                -2.0f at 5.0f with Easing.ExpoOut,
            ),
            y = const(0.0f),
            width = const(2.0f),
            height = const(2.0f),
            cornerRadius = animated(
                0.0f at 0.0f,
                2.0f at 1.0f with Easing.SineInOut,
            ),
            fill = const(
                linear(
                    x0 = -2.0f,
                    y0 = 2.0f,
                    x1 = 2.0f,
                    y1 = -2.0f,
                    colors = listOf(Color(0xFFFA8072), Color(0xFFFCBFB8)),
                )
            ),
        )
    }

    private val player = world + Player()

    override fun update(delta: Float) {
        fps = 1.0f / delta
        world.time = (world.time + timeDirection * delta).coerceAtLeast(0.0f)
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        return when (event.type) {
            KeyEventType.KeyDown -> when (event.key) {
                Key.DirectionRight -> { timeDirection = 1.0f; true }
                Key.DirectionLeft -> { timeDirection = -1.0f; true }
                Key.T -> { player.position = Offset.Zero; true }
                else -> false
            }
            KeyEventType.KeyUp -> when (event.key) {
                Key.DirectionRight -> { timeDirection = 0.0f; true }
                Key.DirectionLeft -> { timeDirection = 0.0f; true }
                else -> false
            }
            else -> false
        }
    }

    override fun DrawScope.draw() {
        drawText(textMeasurer, "fps: $fps\ntime: ${world.time}")
    }
}
