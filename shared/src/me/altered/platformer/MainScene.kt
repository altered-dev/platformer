package me.altered.platformer

import me.altered.koml.Vector2f
import me.altered.platformer.editor.EditorScene
import me.altered.platformer.editor.linear
import me.altered.platformer.editor.solid
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.ui.Button
import me.altered.platformer.engine.ui.Text
import me.altered.platformer.engine.ui.all
import me.altered.platformer.level.ellipse
import me.altered.platformer.level.group
import me.altered.platformer.level.rectangle
import me.altered.platformer.level.Player
import me.altered.platformer.level.world
import me.altered.platformer.timeline.Easing
import me.altered.platformer.timeline.animated
import me.altered.platformer.timeline.at
import me.altered.platformer.timeline.const
import me.altered.platformer.timeline.plus
import me.altered.platformer.timeline.with

class MainScene : Node("main") {

    private var timeDirection = 0.0f
    private var fps = 0.0f

    private val world = +world {
        rectangle(
            name = "floor",
            x = const(750.0f),
            y = const(600.0f),
            width = const(1500.0f),
            height = const(30.0f),
            fill = const(solid(0x80000000)),
        )
        rectangle(
            name = "box",
            x = animated(
                300.0f at 0.0f,
                600.0f at 2.0f with Easing.BackOut,
                100.0f at 5.0f with Easing.ExpoOut,
            ),
            y = const(500.0f),
            width = const(50.0f),
            height = const(50.0f),
            fill = const(linear(-25.0f, 25.0f, 25.0f, -25.0f, 0xFFFA8072, 0xFFFCBFB8)),
        )
        ellipse(
            name = "ellipse",
            x = const(400.0f),
            y = const(450.0f),
            width = const(100.0f),
            height = const(100.0f),
            fill = const(solid(0x80000000)),
        )
        rectangle(
            name = "otherbox",
            x = const(500.0f),
            y = const(250.0f) + const(0.5f),
            rotation = animated(
                0.0f at 0.0f,
                1280.0f at 3.0f with Easing.SineInOut,
            ),
            width = const(40.0f),
            height = const(40.0f),
            fill = const(solid(0xFFB2FFB2))
        )
        group(
            name = "group",
            x = const(800.0f),
            y = const(300.0f),
            rotation = animated(
                0.0f at 0.0f,
                360.0f at 2.0f with Easing.ElasticInOut,
            )
        ) {
            ellipse(
                name = "child",
                x = const(50.0f),
                width = const(20.0f),
                height = const(20.0f),
                fill = animated(
                    solid(0xFFFA8072) at 0.0f,
                    solid(0xFFFCBFB8) at 2.0f with Easing.SineInOut,
                )
            )
            group(
                name = "nested",
                y = const(100.0f),
                rotation = animated(
                    0.0f at 0.0f,
                    -180.0f at 2.0f with Easing.ElasticInOut,
                )
            ) {
                ellipse(
                    name = "nestedChild",
                    x = const(50.0f),
                    width = const(20.0f),
                    height = const(20.0f),
                    fill = animated(
                        solid(0xFFFCBFB8) at 0.0f,
                        solid(0xFFFA8072) at 2.0f with Easing.SineInOut,
                    )
                )
            }
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
                player.position.set(100.0f, 450.0f)
            }
        }
    }
}
