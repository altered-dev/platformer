package me.altered.platformer.objects

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.koml.rotateAround
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.Paint
import me.altered.platformer.engine.util.offset
import me.altered.platformer.engine.util.toPoint
import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.const
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color4f
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Path
import org.jetbrains.skia.Point
import org.jetbrains.skia.Rect

class Polygon(
    name: String,
    override var xExpr: Expression<Float>,
    override var yExpr: Expression<Float>,
    override var rotationExpr: Expression<Float>,
    var fillExpr: Expression<Color4f> = const(Colors.Transparent),
    var strokeExpr: Expression<Color4f> = const(Colors.Transparent),
    var strokeWidthExpr: Expression<Float> = const(0.0f),
    // might replace with floats but require size % 2 == 0
    var points: List<Expression<Vector2fc>>,
) : ObjectNode(name) {

    init {
        require(points.isNotEmpty()) { "A polygon must contain at least two points." }
    }

    private val path = Path()

    private var _points = arrayOf<Point>()

    override val bounds: Rect
        get() = path.bounds.offset(position)

    private val fillPaint = Paint {
        isAntiAlias = true
        mode = PaintMode.FILL
    }

    private val strokePaint = Paint {
        isAntiAlias = true
        mode = PaintMode.STROKE
    }

    override fun eval(time: Float) {
        super.eval(time)
        fillPaint.color4f = fillExpr.eval(time)
        strokePaint.color4f = strokeExpr.eval(time)
        strokePaint.strokeWidth = strokeWidthExpr.eval(time)
        _points = Array(points.size) { i ->
            points[i].eval(time).toPoint()
        }
    }

    override fun draw(canvas: Canvas) {
        canvas
            .drawPath(Path().addPoly(_points, false), fillPaint)
            .drawPolygon(_points, strokePaint)
    }

    override fun collide(position: Vector2fc, radius: Float, onCollision: (point: Vector2fc) -> Unit) {
        if (points.size < 2) return
        val position = position.rotateAround(this.position, rotation)
        var a = _points.last()
        _points.forEach { b ->
            val point = project(position, a.x + this.position.x, a.y + this.position.y, b.x + this.position.x, b.y + this.position.y)
            if (position.distanceSquared(point) <= radius * radius) {
                onCollision(point.rotateAround(this.position, -rotation))
            }
            a = b
        }
    }

    private fun project(p: Vector2fc, ax: Float, ay: Float, bx: Float, by: Float): Vector2fc {
        val dx = bx - ax
        val dy = by - ay
        val d2 = dx * dx + dy * dy
        val nx = (((p.x - ax) * dx + (p.y - ay) * dy) / d2).coerceIn(0.0f, 1.0f)
        return Vector2f(x = dx * nx + ax, y = dy * nx + ay)
    }


    override fun toString(): String = """
        ${super.toString()} (
            x = $xExpr
            y = $yExpr
            rotation = $rotationExpr
            fill = $fillExpr
            stroke = $strokeExpr
            strokeWidth = $strokeWidthExpr
            points:
            ${points.joinToString("\n            ")}
        )
    """.trimIndent()
}
