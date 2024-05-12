package me.altered.platformer.`object`

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skia.Color4f
import org.jetbrains.skia.PaintMode
import me.altered.platformer.engine.node.Node
import me.altered.platformer.skija.buildPaint
import me.altered.platformer.skija.buildPath
import me.altered.platformer.skija.color
import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.const

class Polygon(
    // TODO: calculate based on points
    var x: Expression<Float>,
    var y: Expression<Float>,
    var rotation: Expression<Float>,
    vararg var points: Expression<Float>,
    var fill: Expression<Color4f> = const(color(Color.TRANSPARENT)),
    var stroke: Expression<Color4f> = const(color(Color.TRANSPARENT)),
    var strokeWidth: Expression<Float> = const(0.0f),
) : Node("polygon") {

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
        val poly = FloatArray(points.size) { points[it].value }
        val path = buildPath { addPoly(poly, true) }
        canvas
            .translate(x.value, y.value)
            .rotate(rotation.value)
            .drawPath(path, fillPaint)
            .drawPath(path, strokePaint)
    }
}
