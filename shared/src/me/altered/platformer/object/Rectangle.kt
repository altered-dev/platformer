package me.altered.platformer.`object`

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color4f
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.util.paint
import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.const
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.timeline.Timeline
import kotlin.math.withSign

class Rectangle(
    val timeline: Timeline,
    var x: Expression<Float>,
    var y: Expression<Float>,
    var width: Expression<Float>,
    var height: Expression<Float>,
    var rotation: Expression<Float>,
    var fill: Expression<Color4f> = const(Colors.transparent),
    var stroke: Expression<Color4f> = const(Colors.transparent),
    var strokeWidth: Expression<Float> = const(0.0f),
) : Node("rectangle") {

    private val fillPaint = paint {
        isAntiAlias = true
        mode = PaintMode.FILL
    }

    private val strokePaint = paint {
        isAntiAlias = true
        mode = PaintMode.STROKE
    }

    override fun draw(canvas: Canvas, bounds: Rect) {
        val time = timeline.time
        fillPaint.color4f = fill.eval(time)
        strokePaint.color4f = stroke.eval(time)
        strokePaint.strokeWidth = strokeWidth.eval(time)
        val w = this.width.eval(time) * 0.5f
        val h = this.height.eval(time) * 0.5f
        val rect = Rect.makeLTRB(w.withSign(-1), h.withSign(-1), w.withSign(1), h.withSign(1))
        canvas
            .translate(x.eval(time), y.eval(time))
            .rotate(rotation.eval(time))
            .drawRect(rect, fillPaint)
            // TODO: stroke modes: outside, center, inside
            .drawRect(rect, strokePaint)
    }
}
