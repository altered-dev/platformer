package me.altered.platformer

import me.altered.koml.Vector2f
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.node.Alignment
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.Node2D
import me.altered.platformer.engine.node.SceneManager
import me.altered.platformer.engine.node.UiNode
import me.altered.platformer.engine.node.all
import me.altered.platformer.engine.node.px
import me.altered.platformer.engine.node.vertical
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.player.Player
import me.altered.platformer.timeline.Timeline
import me.altered.platformer.ui.Button
import me.altered.platformer.ui.Text
import org.jetbrains.skia.Rect

class MainScene : Node("main") {

    override val name = "main"

    private val timeline = Timeline()
    private var timeDirection = 0.0f
    private var fps = 0.0f

    private val player = +Player(position = Vector2f(100.0f, 300.0f))

    private val button = +Button(
        rect = Rect.makeXYWH(50.0f, 100.0f, 128.0f, 36.0f),
        name = "Go to editor",
        onClick = { SceneManager.scene = EditorScene() },
    )

    private val time = +Text({ "time: ${timeline.time}" }, 10.0f, 40.0f, Colors.black)
    private val fpsText = +Text({ "fps: $fps" }, 10.0f, 60.0f, Colors.black)

    private val other: Node2D
    private val node2d = +Node2D(
        name = "node2d",
        position = Vector2f(500.0f, 200.0f),
    ).apply {
        other = +Node2D(
            name = "other",
            position = Vector2f(100.0f, 100.0f),
        )
    }

    private val uiNode = +UiNode(
        name = "uinode",
        height = 256.px,
        margin = all(30.0f),
    ).apply {
        +UiNode(
            name = "yay",
            width = 128.px,
            halign = Alignment.CENTER,
            margin = vertical(30.0f),
        )
    }

    override fun update(delta: Float) {
        fps = 1.0f / delta
        timeline.time += timeDirection * delta
        node2d.rotation += 45.0f * delta
        other.position.x -= 10.0f * delta
        node2d.scale.x += timeDirection * delta
        uiNode.margin = all(uiNode.margin.left + timeDirection * 10.0f * delta)
    }

    override fun input(event: InputEvent) {
        when {
            event pressed Key.RIGHT -> timeDirection += 1
            event released Key.RIGHT -> timeDirection -= 1
            event pressed Key.LEFT -> timeDirection -= 1
            event released Key.LEFT -> timeDirection += 1
        }
    }
}
