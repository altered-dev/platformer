package me.altered.platformer.`object`

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color4f
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect
import me.altered.platformer.engine.util.paint
import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.const
import me.altered.platformer.engine.util.Colors
import kotlin.math.withSign

class Rectangle(
    override var xExpr: Expression<Float>,
    override var yExpr: Expression<Float>,
    override var rotationExpr: Expression<Float>,
    var widthExpr: Expression<Float>,
    var heightExpr: Expression<Float>,
    var fillExpr: Expression<Color4f> = const(Colors.transparent),
    var strokeExpr: Expression<Color4f> = const(Colors.transparent),
    var strokeWidthExpr: Expression<Float> = const(0.0f),
) : ObjectNode("rectangle") {

    // TODO: constraint to non-negative
    var width: Float = 0.0f
    var height: Float = 0.0f

    override val bounds: Rect
        get() {
            val w = width * 0.5f
            val h = height * 0.5f
            return Rect.makeLTRB(w.withSign(-1), h.withSign(-1), w.withSign(1), h.withSign(1))
        }

    private val fillPaint = paint {
        isAntiAlias = true
        mode = PaintMode.FILL
    }

    private val strokePaint = paint {
        isAntiAlias = true
        mode = PaintMode.STROKE
    }

    override fun draw(canvas: Canvas, bounds: Rect) {
        val rect = this.bounds
        canvas
            .drawRect(rect, fillPaint)
            // TODO: stroke modes: outside, center, inside
            .drawRect(rect, strokePaint)
    }

    override fun eval(time: Float) {
        super.eval(time)
        width = widthExpr.eval(time)
        height = heightExpr.eval(time)
        fillPaint.color4f = fillExpr.eval(time)
        strokePaint.color4f = strokeExpr.eval(time)
        strokePaint.strokeWidth = strokeWidthExpr.eval(time)
    }
}
