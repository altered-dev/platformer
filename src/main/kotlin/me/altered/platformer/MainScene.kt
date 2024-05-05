package me.altered.platformer

import io.github.humbleui.skija.Canvas
import io.github.humbleui.skija.Font
import io.github.humbleui.skija.FontMgr
import io.github.humbleui.skija.FontStyle
import io.github.humbleui.skija.impl.Stats
import io.github.humbleui.types.Rect
import me.altered.platformer.glfw.window.Window
import me.altered.platformer.glfw.input.InputEvent
import me.altered.platformer.glfw.input.Key
import me.altered.platformer.glfw.input.pressed
import me.altered.platformer.glfw.input.released
import me.altered.platformer.glfw.input.scrolled
import me.altered.platformer.glfw.window.Window.Attributes.focused
import me.altered.platformer.`object`.Object
import me.altered.platformer.player.Player
import me.altered.platformer.node.ParentNode
import me.altered.platformer.node.getValue
import me.altered.platformer.node.provideDelegate
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.buildPaint
import me.altered.platformer.timeline.Timeline
import me.altered.platformer.ui.Alignment
import me.altered.platformer.ui.Button
import me.altered.platformer.ui.Container
import me.altered.platformer.ui.Size
import me.altered.platformer.ui.all
import me.altered.platformer.ui.center
import me.altered.platformer.ui.end
import me.altered.platformer.ui.expand
import me.altered.platformer.ui.px
import me.altered.platformer.ui.start
import org.joml.Vector2f

class MainScene(
    override val window: Window,
) : ParentNode() {

    override val name = "main"

    private val timeline = Timeline()
    private var timeDirection = 0.0f
    private var fps = 0.0f

    private val player by Player(Vector2f(100.0f, 300.0f))
    private val obj by Object(timeline, "omg")

    private val button by Button(
        rect = Rect.makeXYWH(10.0f, 100.0f, 128.0f, 48.0f),
        name = "First button!"
    ) {
        if (container.horizontalAlignment == Alignment.END) {
            container.height = when (container.height) {
                is Size.Fixed -> expand
                Size.Wrap -> 0.px
                Size.Expand -> 120.px
            }
        }
        container.horizontalAlignment = when (container.horizontalAlignment) {
            Alignment.START -> center
            Alignment.CENTER -> end
            Alignment.END -> start
        }
    }

    private val container by Container(width = 120.px, height = expand, margin = all(10.px))

    private val font = FontMgr.getDefault().matchFamilyStyle("NT Somic", FontStyle.NORMAL)?.let { Font(it, 13.0f) }
    private val paint = buildPaint { color4f = Color.black }

    override fun update(delta: Float) {
        fps = 1.0f / delta
        timeline.time += timeDirection * delta
    }

    override fun draw(canvas: Canvas) {
        canvas.drawString("time: ${timeline.time}", 10.0f, 40.0f, font, paint)
        canvas.drawString("fps: $fps", 10.0f, 60.0f, font, paint)
        canvas.drawString("native calls: ${Stats.nativeCalls}", 10.0f, 80.0f, font, paint)
        canvas.drawString("focused: ${window.focused}", 10.0f, 100.0f, font, paint)
    }

    override fun input(event: InputEvent): Boolean {
        when {
            event pressed Key.RIGHT -> timeDirection += 1
            event released Key.RIGHT -> timeDirection -= 1
            event pressed Key.LEFT -> timeDirection -= 1
            event released Key.LEFT -> timeDirection += 1
            event.scrolled() -> container.margin = all((container.margin.top + event.dy).px)
        }
        return false
    }
}
