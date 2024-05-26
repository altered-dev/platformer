package me.altered.platformer.`object`

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color4f
import org.jetbrains.skia.PaintMode
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.util.paint
import me.altered.platformer.engine.util.path
import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.const
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.timeline.Timeline
import org.jetbrains.skia.Rect

class Polygon(
    val timeline: Timeline,
    // TODO: calculate based on points
    var x: Expression<Float>,
    var y: Expression<Float>,
    var rotation: Expression<Float>,
    vararg var points: Expression<Float>,
    var fill: Expression<Color4f> = const(Colors.transparent),
    var stroke: Expression<Color4f> = const(Colors.transparent),
    var strokeWidth: Expression<Float> = const(0.0f),
) : Node("polygon") {

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
        val poly = FloatArray(points.size) { points[it].eval(time) }
        val path = path { addPoly(poly, true) }
        canvas
            .translate(x.eval(time), y.eval(time))
            .rotate(rotation.eval(time))
            .drawPath(path, fillPaint)
            .drawPath(path, strokePaint)
    }
}
