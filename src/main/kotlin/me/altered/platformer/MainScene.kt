package me.altered.platformer

import io.github.humbleui.skija.Canvas
import io.github.humbleui.skija.Font
import io.github.humbleui.skija.FontMgr
import io.github.humbleui.skija.FontStyle
import io.github.humbleui.types.Rect
import me.altered.platformer.glfw.input.InputEvent
import me.altered.platformer.glfw.input.Key
import me.altered.platformer.glfw.input.pressed
import me.altered.platformer.glfw.input.released
import me.altered.platformer.`object`.Object
import me.altered.platformer.player.Player
import me.altered.platformer.scene.ParentNode
import me.altered.platformer.scene.getValue
import me.altered.platformer.scene.provideDelegate
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.buildPaint
import me.altered.platformer.timeline.Timeline
import me.altered.platformer.ui.Button
import org.joml.Vector2f

class MainScene : ParentNode() {

    override val name = "main"

    private val timeline = Timeline()
    private var timeDirection = 0.0f
    private var fps = 0.0f

    private val player by Player(Vector2f(100.0f, 300.0f))
    private val obj by Object(timeline, "omg")

    private val button by Button(Rect.makeXYWH(10.0f, 100.0f, 128.0f, 48.0f), "First button!") {
        println("button pressed")
    }

    private val font = FontMgr.getDefault().matchFamilyStyle("NT Somic", FontStyle.NORMAL)?.let { Font(it, 13.0f) }
    private val paint = buildPaint { color4f = Color.black }

    override fun update(delta: Float) {
        fps = 1.0f / delta
        timeline.time += timeDirection * delta
    }

    override fun draw(canvas: Canvas) {
        canvas.drawString("time: ${timeline.time}", 10.0f, 40.0f, font, paint)
        canvas.drawString("fps: $fps", 10.0f, 60.0f, font, paint)
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
}
