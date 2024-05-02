package me.altered.platformer.`object`

import io.github.humbleui.skija.Canvas
import io.github.humbleui.types.Rect
import me.altered.platformer.scene.Node
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.buildPaint
import me.altered.platformer.timeline.AnimatedFloat
import me.altered.platformer.timeline.Easing
import me.altered.platformer.timeline.Timeline
import me.altered.platformer.timeline.at
import me.altered.platformer.timeline.const
import me.altered.platformer.timeline.plus
import me.altered.platformer.timeline.with

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
    ) + AnimatedFloat(
        timeline = timeline,
        const(100.0f) at 0.5f,
        const(0.0f) at 2.0f with Easing.expoInOut,
    )

    private val paint = buildPaint {
        color4f = Color.black
    }

    override fun draw(canvas: Canvas) {
        val rect = Rect.makeLTRB(-10f, -10f, 10f, 10f)
        canvas.apply {
            translate(x.value, y.value)
            drawRect(rect, paint)
        }
    }
}
