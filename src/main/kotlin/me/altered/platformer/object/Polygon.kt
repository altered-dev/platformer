package me.altered.platformer.`object`

import io.github.humbleui.skija.Canvas
import io.github.humbleui.skija.Color4f
import io.github.humbleui.skija.PaintMode
import me.altered.platformer.node.Node
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.buildPaint
import me.altered.platformer.skija.buildPath
import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.const

class Polygon(
    // TODO: calculate based on points
    var x: Expression<Float>,
    var y: Expression<Float>,
    var rotation: Expression<Float>,
    vararg var points: Expression<Float>,
    var fill: Expression<Color4f> = const(Color.transparent),
    var stroke: Expression<Color4f> = const(Color.transparent),
    var strokeWidth: Expression<Float> = const(0.0f),
) : Node() {

    private val fillPaint = buildPaint {
        isAntiAlias = true
        mode = PaintMode.FILL
    }

    private val strokePaint = buildPaint {
        isAntiAlias = true
        mode = PaintMode.STROKE
    }

    override fun draw(canvas: Canvas) {
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
