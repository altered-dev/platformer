package me.altered.platformer.`object`

import io.github.humbleui.skija.Canvas
import io.github.humbleui.skija.Color4f
import io.github.humbleui.types.Rect
import me.altered.platformer.glfw.input.InputEvent
import me.altered.platformer.scene.Node
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.buildPaint
import me.altered.platformer.skija.contains
import me.altered.platformer.timeline.AnimatedFloat
import me.altered.platformer.timeline.Easing
import me.altered.platformer.timeline.Timeline
import me.altered.platformer.timeline.at
import me.altered.platformer.timeline.const
import me.altered.platformer.timeline.minus
import me.altered.platformer.timeline.with
import org.joml.Vector2f

class Object(
    timeline: Timeline,
    override val name: String = "object",
) : Node() {

    private val x = AnimatedFloat(
        timeline = timeline,
        const(300.0f) at 0.5f,
        const(500.0f) at 1.0f with Easing.sineOut,
    )

    private val y = AnimatedFloat(
        timeline = timeline,
        const(500.0f) at 0.5f,
        const(300.0f) at 1.0f with Easing.sineIn,
        const(300.0f) at 1.5f,
        const(500.0f) at 2.0f with Easing.expoInOut,
    ) - AnimatedFloat(
        timeline = timeline,
        const(100.0f) at 0.5f,
        const(0.0f) at 2.0f with Easing.expoInOut,
    )

    private val paint = buildPaint {
        color4f = Color.black
    }

    private val rect = Rect.makeLTRB(-10f, -10f, 10f, 10f)

    override fun draw(canvas: Canvas) {
        canvas.apply {
            translate(x.value, y.value)
            drawRect(rect, paint)
        }
    }

    override fun input(event: InputEvent): Boolean {
        if (event !is InputEvent.CursorMove) return false
        val position = Vector2f(event.x.toFloat(), event.y.toFloat())
        val newRect = rect.offset(x.value, y.value)

        if (position in newRect) {
            paint.color4f = Color4f(1.0f, 0.0f, 0.0f)
            return true
        } else {
            paint.color4f = Color.black
            return false
        }
    }
}
