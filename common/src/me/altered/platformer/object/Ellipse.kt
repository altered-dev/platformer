package me.altered.platformer.`object`

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color4f
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect
import me.altered.platformer.engine.node.Node
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.buildPaint
import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.const
import kotlin.math.withSign

class Ellipse(
    var x: Expression<Float>,
    var y: Expression<Float>,
    var width: Expression<Float>,
    var height: Expression<Float>,
    var rotation: Expression<Float>,
    var fill: Expression<Color4f> = const(Color.transparent),
    var stroke: Expression<Color4f> = const(Color.transparent),
    var strokeWidth: Expression<Float> = const(0.0f),
) : Node("ellipse") {

    private val fillPaint = buildPaint {
        isAntiAlias = true
        mode = PaintMode.FILL
    }

    private val strokePaint = buildPaint {
        isAntiAlias = true
        mode = PaintMode.STROKE
    }

    override fun draw(canvas: Canvas, width: Float, height: Float) {
        fillPaint.color4f = fill.value
        strokePaint.color4f = stroke.value
        strokePaint.strokeWidth = strokeWidth.value
        val w = this.width.value * 0.5f
        val h = this.height.value * 0.5f
        val rect = Rect.makeLTRB(w.withSign(-1), h.withSign(-1), w.withSign(1), h.withSign(1))
        canvas
            .translate(x.value, y.value)
            .rotate(rotation.value)
            .drawOval(rect, fillPaint)
            // TODO: stroke modes: outside, center, inside
            .drawOval(rect, strokePaint)
    }
}
