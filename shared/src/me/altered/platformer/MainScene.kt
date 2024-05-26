package me.altered.platformer

import me.altered.koml.Vector2f
import me.altered.platformer.editor.EditorScene
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.Node2D
import me.altered.platformer.engine.node.SceneManager
import me.altered.platformer.engine.node.SceneManager.defer
import me.altered.platformer.engine.node.all
import me.altered.platformer.engine.node.each
import me.altered.platformer.engine.node.px
import me.altered.platformer.engine.node.ui.Button
import me.altered.platformer.engine.node.ui.Text
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.`object`.Rectangle
import me.altered.platformer.player.Player
import me.altered.platformer.timeline.Easing
import me.altered.platformer.timeline.Timeline
import me.altered.platformer.timeline.animated
import me.altered.platformer.timeline.at
import me.altered.platformer.timeline.const
import me.altered.platformer.timeline.with
import kotlin.math.min

class MainScene(
    private val timeline: Timeline = Timeline(),
    objects: Set<Node> = setOf(
        Rectangle(
            timeline = timeline,
            x = animated(
                100.0f at 0.0f,
                200.0f at 1.0f with Easing.cubicInOut,
                300.0f at 2.0f with Easing.expoInOut,
                400.0f at 3.0f with { min(it, 1.0f - it) },
            ),
            y = const(100.0f),
            width = const(10.0f),
            height = const(10.0f),
            rotation = const(0.0f),
            fill = const(Colors.black),
        )
    ),
) : Node("main") {

    private var timeDirection = 0.0f
    private var fps = 0.0f

    private val player: Player
    private val _objects: Node2D

    private val world = +Node2D("world").apply {
        _objects = +Node2D("objects").apply {
            addChildren(objects)
        }
        player = +Player(position = Vector2f(100.0f, 300.0f))
    }

    private val time = +Text("time: ${timeline.time}", margin = each(left = 16.0f, top = 32.0f))
    private val fpsText = +Text("fps: $fps", margin = each(left = 16.0f, top = 56.0f))

    private val button = +Button(
        text = "Open editor",
        width = 128.px,
        height = 32.px,
        margin = all(128.0f),
    )

    override fun update(delta: Float) {
        fps = 1.0f / delta
        timeline.time += timeDirection * delta
        time.text = "time: ${timeline.time}"
        fpsText.text = "Ffps: $fps"
    }

    override fun input(event: InputEvent) {
        when {
            event pressed Key.RIGHT -> timeDirection += 1
            event released Key.RIGHT -> timeDirection -= 1
            event pressed Key.LEFT -> timeDirection -= 1
            event released Key.LEFT -> timeDirection += 1
            event pressed Key.E -> {
                val children = _objects.children
                defer { SceneManager.scene = EditorScene(timeline, children) }
            }
        }
    }
}
