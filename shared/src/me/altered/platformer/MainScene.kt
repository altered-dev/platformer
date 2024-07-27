package me.altered.platformer

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.platformer.editor.EditorScene
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.ui.Button
import me.altered.platformer.engine.ui.Text
import me.altered.platformer.engine.ui.all
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.Paint
import me.altered.platformer.objects.World
import me.altered.platformer.objects.ellipse
import me.altered.platformer.objects.group
import me.altered.platformer.objects.rectangle
import me.altered.platformer.player.Player
import me.altered.platformer.timeline.Easing
import me.altered.platformer.timeline.animated
import me.altered.platformer.timeline.at
import me.altered.platformer.timeline.const
import me.altered.platformer.timeline.with

class MainScene : Node("main") {

    private var timeDirection = 0.0f
    private var fps = 0.0f

    private val world = +World(
        rectangle(
            x = const(600.0f),
            y = const(520.0f),
            rotation = const(0.0f),
            width = const(1500.0f),
            height = const(50.0f),
            fill = const(Colors.Green),
        ),
        rectangle(
            x = const(500.0f),
            y = const(480.0f),
            rotation = animated(
                0.0f at 0.0f,
                360.0f at 3.0f with Easing.Linear,
            ),
            width = const(150.0f),
            height = const(30.0f),
            fill = const(Colors.Black),
        )
    )

    private val player1 = world + Player(position = Vector2f(100.0f, 300.0f))
//    private val player2 = world + Player(position = Vector2f(100.0f, 300.0f)).also { it.applyVelAtUpdate = true }

    private val time = +Text("time: 0", anchor = Vector2f(0.05f, 0.05f))
    private val fpsText = +Text("fps: 0", anchor = Vector2f(0.05f, 0.075f))

    private val button = +Button(
        text = "hello world",
        padding = all(16.0f),
        anchor = Vector2f(0.1f, 0.1f),
        onClick = { tree?.scene = EditorScene() }
    )

    override fun update(delta: Float) {
        fps = 1.0f / delta
        world.time += timeDirection * delta
        time.text = "time: ${world.time}"
        fpsText.text = "fps: $fps"
    }

    fun makeCollisions(position: Vector2fc, radius: Float): List<Vector2fc> {
        return world.objects.mapNotNull { obj ->
            obj.collide(position, radius)
        }
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
        }
    }
}
