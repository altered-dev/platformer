package me.altered.platformer

import me.altered.koml.Vector2f
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.io.font
import me.altered.platformer.engine.node.ParentNode
import me.altered.platformer.engine.node.SceneManager
import me.altered.platformer.engine.node.getValue
import me.altered.platformer.engine.node.provideDelegate
import me.altered.platformer.player.Player
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.buildPaint
import me.altered.platformer.timeline.Timeline
import me.altered.platformer.ui.Button
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect

class MainScene: ParentNode("main") {

    override val name = "main"

    private val timeline = Timeline()
    private var timeDirection = 0.0f
    private var fps = 0.0f

    private val player by Player(Vector2f(100.0f, 300.0f))

    private val button by Button(
        rect = Rect.makeXYWH(50.0f, 100.0f, 128.0f, 36.0f),
        name = "Go to editor",
        onClick = { SceneManager.scene = EditorScene() },
    )

    private val paint = buildPaint { color4f = Color.black }

    override fun update(delta: Float) {
        fps = 1.0f / delta
        timeline.time += timeDirection * delta
    }

    override fun draw(canvas: Canvas, width: Float, height: Float) {
        canvas.drawString("time: ${timeline.time}", 10.0f, 40.0f, font, paint)
        canvas.drawString("fps: $fps", 10.0f, 60.0f, font, paint)
    }

    override fun input(event: InputEvent) {
        when {
            event pressed Key.RIGHT -> timeDirection += 1
            event released Key.RIGHT -> timeDirection -= 1
            event pressed Key.LEFT -> timeDirection -= 1
            event released Key.LEFT -> timeDirection += 1
        }
    }

    companion object {

        private val font = font("fonts/Inter-Regular.ttf", 13.0f)
    }
}
