package me.altered.platformer.objects

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color4f
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect
import me.altered.platformer.engine.util.Paint
import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.const
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.offset
import kotlin.math.withSign

class Rectangle(
    name: String,
    override var xExpr: Expression<Float>,
    override var yExpr: Expression<Float>,
    override var rotationExpr: Expression<Float>,
    var widthExpr: Expression<Float>,
    var heightExpr: Expression<Float>,
    var fillExpr: Expression<Color4f> = const(Colors.Transparent),
    var strokeExpr: Expression<Color4f> = const(Colors.Transparent),
    var strokeWidthExpr: Expression<Float> = const(0.0f),
) : ObjectNode(name) {

    // TODO: constraint to non-negative
    var width: Float = 0.0f
    var height: Float = 0.0f

    override val bounds: Rect
        get() {
            val w = width * 0.5f
            val h = height * 0.5f
            return Rect.makeLTRB(w.withSign(-1), h.withSign(-1), w.withSign(1), h.withSign(1))
        }

    private val fillPaint = Paint {
        isAntiAlias = true
        mode = PaintMode.FILL
    }

    private val strokePaint = Paint {
        isAntiAlias = true
        mode = PaintMode.STROKE
    }

    override fun draw(canvas: Canvas) {
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

    override fun collide(position: Vector2fc, radius: Float, onCollision: (point: Vector2fc) -> Unit) {
        rotated(position) { position ->
            val bounds = bounds.offset(this.position)
            val x = position.x.coerceIn(bounds.left, bounds.right)
            val y = position.y.coerceIn(bounds.top, bounds.bottom)
            val vec = Vector2f(x, y)
            if (vec.distanceSquared(position) > (radius * radius)) {
                return
            }
            vec
        }.let(onCollision)
    }
}
