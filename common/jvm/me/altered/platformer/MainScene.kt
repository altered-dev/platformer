package me.altered.platformer

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Font
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Rect
import me.altered.platformer.glfw.input.InputEvent
import me.altered.platformer.glfw.input.Key
import me.altered.platformer.glfw.input.pressed
import me.altered.platformer.glfw.input.released
import me.altered.platformer.glfw.window.Window
import me.altered.platformer.io.font
import me.altered.platformer.io.resource
import me.altered.platformer.node.ParentNode
import me.altered.platformer.node.SceneManager
import me.altered.platformer.node.getValue
import me.altered.platformer.node.provideDelegate
import me.altered.platformer.player.Player
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.buildPaint
import me.altered.platformer.timeline.Timeline
import me.altered.platformer.ui.Button
import org.joml.Vector2f

class MainScene(
    override val window: Window,
) : ParentNode() {

    override val name = "main"

    private val timeline = Timeline()
    private var timeDirection = 0.0f
    private var fps = 0.0f

    private val lol = resource("fonts/lol.txt")

    private val player by Player(Vector2f(100.0f, 300.0f))

    private val button by Button(
        rect = Rect.makeXYWH(50.0f, 100.0f, 128.0f, 36.0f),
        name = "Go to editor",
        onClick = { SceneManager.scene = EditorScene(window) },
    )

    private val paint = buildPaint { color4f = Color.black }

    override fun update(delta: Float) {
        fps = 1.0f / delta
        timeline.time += timeDirection * delta
    }

    override fun draw(canvas: Canvas) {
        canvas.drawString("time: ${timeline.time}", 10.0f, 40.0f, font, paint)
        canvas.drawString("fps: $fps", 10.0f, 60.0f, font, paint)
        canvas.drawString("lol: $lol", 10.0f, 80.0f, font, paint)
    }

    override fun input(event: InputEvent): Boolean {
        when {
            event pressed Key.RIGHT -> timeDirection += 1
            event released Key.RIGHT -> timeDirection -= 1
            event pressed Key.LEFT -> timeDirection -= 1
            event released Key.LEFT -> timeDirection += 1
        }
        return false
    }

    companion object {

        private val font = font("fonts/Inter-Regular.ttf", 13.0f)
    }
}
