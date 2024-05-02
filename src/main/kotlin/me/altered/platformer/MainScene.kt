package me.altered.platformer

import io.github.humbleui.skija.Canvas
import io.github.humbleui.skija.Font
import io.github.humbleui.skija.FontMgr
import io.github.humbleui.skija.FontStyle
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
import org.joml.Vector2f

class MainScene : ParentNode() {

    override val name = "main"

    private val timeline = Timeline()
    private var timeDirection = 0.0f

    private val player by Player(Vector2f(100.0f, 300.0f))
    private val obj by Object(timeline, "omg")

    private val font = FontMgr.getDefault().matchFamilyStyle("NT Somic", FontStyle.NORMAL)?.let { Font(it, 13.0f) }
    private val paint = buildPaint { color4f = Color.black }

    override fun update(delta: Float) {
        timeline.time += timeDirection * delta
    }

    override fun draw(canvas: Canvas) {
        canvas.drawString("time: ${timeline.time}", 10.0f, 40.0f, font, paint)
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
