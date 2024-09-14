package me.altered.platformer

import me.altered.koml.Vector2f
import me.altered.platformer.editor.EditorScene
import me.altered.platformer.editor.Grid
import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.ui.Button
import me.altered.platformer.engine.ui.Text
import me.altered.platformer.engine.ui.padding
import me.altered.platformer.level.Player
import me.altered.platformer.level.data.linear
import me.altered.platformer.level.data.solid
import me.altered.platformer.level.rectangle
import me.altered.platformer.level.world
import me.altered.platformer.timeline.Easing
import me.altered.platformer.timeline.animated
import me.altered.platformer.timeline.at
import me.altered.platformer.timeline.const
import me.altered.platformer.timeline.with

class MainScene : Node("main") {

    private var timeDirection = 0.0f
    private var fps = 0.0f

    private val world = +world {
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

    private val grid = world + Grid()
    private val player = world + Player()

    private val time = +Text("time: 0", anchor = Vector2f(0.05f, 0.05f))
    private val fpsText = +Text("fps: 0", anchor = Vector2f(0.05f, 0.075f))

    private val button = +Button(
        text = "hello world",
        padding = padding(16.0f),
        anchor = Vector2f(0.1f, 0.1f),
        onClick = { tree?.scene = EditorScene() }
    )

    override fun update(delta: Float) {
        fps = 1.0f / delta
        world.time = (world.time + timeDirection * delta).coerceAtLeast(0.0f)
        time.text = "time: ${world.time}"
        fpsText.text = "fps: $fps"
    }

    override fun input(event: InputEvent) {
        when {
            event pressed Key.Right -> timeDirection += 1
            event released Key.Right -> timeDirection -= 1
            event pressed Key.Left -> timeDirection -= 1
            event released Key.Left -> timeDirection += 1
            event pressed Key.E -> {
                tree?.scene = EditorScene()
            }
            event pressed Key.Q -> {
                tree?.scene = EasingScene()
            }
            event pressed Key.T -> {
                player.position.set(0.0f, 0.0f)
            }
        }
    }
}
