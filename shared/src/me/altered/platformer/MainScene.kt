package me.altered.platformer

import me.altered.koml.Vector2f
import me.altered.platformer.editor.EditorScene
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.ui.Button
import me.altered.platformer.engine.ui.Text
import me.altered.platformer.engine.ui.all
import me.altered.platformer.player.Player
import me.altered.platformer.timeline.Timeline

class MainScene(
    private val timeline: Timeline = Timeline(),
) : Node("main") {

    private var timeDirection = 0.0f
    private var fps = 0.0f

    private val player: Player
    private val _objects: Node2D

    private val world = +Node2D("world").apply {
        _objects = +Node2D("objects").apply {
        }
        player = +Player(position = Vector2f(100.0f, 300.0f))
    }

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
        timeline.time += timeDirection * delta
        time.text = "time: ${timeline.time}"
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
        }
    }
}
