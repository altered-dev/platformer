package me.altered.platformer

import me.altered.koml.Vector2f
import me.altered.platformer.editor.EditorScene
import me.altered.platformer.editor.linear
import me.altered.platformer.editor.solid
import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.ui.Button
import me.altered.platformer.engine.ui.Text
import me.altered.platformer.engine.ui.all
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.objects.ellipse
import me.altered.platformer.objects.group
import me.altered.platformer.objects.polygon
import me.altered.platformer.objects.rectangle
import me.altered.platformer.objects.world
import me.altered.platformer.player.Player
import me.altered.platformer.timeline.Easing
import me.altered.platformer.timeline.animated
import me.altered.platformer.timeline.at
import me.altered.platformer.timeline.const
import me.altered.platformer.timeline.point
import me.altered.platformer.timeline.reference
import me.altered.platformer.timeline.with
import me.altered.platformer.timeline.x

class MainScene : Node("main") {

    private var timeDirection = 0.0f
    private var fps = 0.0f

    private val world = +world {
        rectangle(
            name = "blue",
            x = animated(
                300.0f at 0.0f,
                200.0f at 0.5f with Easing.BounceOut,
            ),
            y = reference("black", x),
            rotation = const(0.0f),
            width = const(30.0f),
            height = const(30.0f),
            fill = const(solid(Color.Blue)),
        )
        rectangle(
            name = "green",
            x = const(600.0f),
            y = const(520.0f),
            rotation = const(0.0f),
            width = const(1500.0f),
            height = const(50.0f),
            fill = animated(
                solid(Color.Green) at 0.0f,
                solid(Color.Magenta) at 2.0f with Easing.SineInOut,
            ),
        )
        rectangle(
            name = "black",
            x = animated(
                500.0f at 0.0f,
                400.0f at 2.0f with Easing.SineInOut,
            ),
            y = const(480.0f),
            rotation = animated(
                0.0f at 0.0f,
                360.0f at 3.0f with Easing.SineInOut,
            ),
            width = const(150.0f),
            height = const(30.0f),
            fill = const(linear(-75.0f, 0.0f, 75.0f, 0.0f, Color.Black, Color.Cyan)),
            stroke = const(linear(-75.0f, 0.0f, 75.0f, 0.0f, Color.Cyan, Color.Black)),
            strokeWidth = const(2.0f),
        )
        rectangle(
            name = "magenta",
            x = const(800.0f),
            y = animated(
                460.0f at 0.0f,
                400.0f at 1.0f with Easing.SineInOut,
                400.0f at 3.0f,
                460.0f at 4.0f with Easing.SineInOut,
                460.0f at 6.0f,
            ),
            rotation = const(0.0f),
            width = const(100.0f),
            height = const(30.0f),
            fill = const(solid(Color.Magenta)),
        )
        ellipse(
            name = "ellipse",
            x = const(650.0f),
            y = const(480.0f),
            rotation = animated(
                0.0f at 0.0f,
                180.0f at 2.0f,
            ),
            width = const(150.0f),
            height = const(100.0f),
            fill = const(solid(Color.Red)),
        )
        polygon(
            name = "polygon",
            x = const(350.0f),
            y = const(400.0f),
            rotation = animated(
                0.0f at 0.0f,
                90.0f at 1.0f with Easing.ExpoInOut,
            ),
            point(0.0f, 0.0f),
            point(
                x = animated(
                    50.0f at 0.0f,
                    100.0f at 1.0f with Easing.ExpoInOut,
                ),
                y = const(0.0f),
            ),
            point(0.0f, 50.0f),
            fill = const(Colors.Black),
        )
        group(
            name = "lol",
            x = const(300.0f),
            y = const(300.0f),
            rotation = const(0.0f),
        ) {
            ellipse(
                name = "moreellipse",
                x = const(650.0f),
                y = const(480.0f),
                rotation = animated(
                    0.0f at 0.0f,
                    180.0f at 2.0f,
                ),
                width = const(150.0f),
                height = const(100.0f),
                fill = const(solid(Color.Red)),
            )
        }
    }

    private val player = world + Player(position = Vector2f(100.0f, 450.0f))

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
                player.position.set(100.0f, 450.0f)
            }
        }
    }
}
