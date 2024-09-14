package me.altered.platformer.level.node

import me.altered.koml.Vector2fc
import me.altered.platformer.engine.graphics.Paint
import me.altered.platformer.engine.graphics.drawOval
import me.altered.platformer.level.data.toShader
import me.altered.platformer.engine.node.Node
import me.altered.platformer.level.TimeContext
import me.altered.platformer.level.data.Ellipse
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode

class EllipseNode(
    obj: Ellipse? = null,
    parent: Node? = null,
) : ObjectNode<Ellipse>(obj, parent) {

    private val fillPaint = Paint { mode = PaintMode.FILL }
    private val strokePaint = Paint { mode = PaintMode.STROKE }

    override fun TimeContext.eval() {
        val obj = obj ?: return
        position.set(
            x = obj.x.value,
            y = obj.y.value,
        )
        rotation = obj.rotation.value
        bounds = baseBounds.scale(
            sx = obj.width.value,
            sy = obj.height.value,
        )
        // the toShader call is very expensive, needs caching
        fillPaint.shader = obj.fill.value.toShader()
        strokePaint.shader = obj.stroke.value.toShader()
        strokePaint.strokeWidth = obj.strokeWidth.value
    }

    override fun collide(position: Vector2fc, radius: Float, onCollision: (point: Vector2fc) -> Unit) {
        if (bounds.width <= 0.0f || bounds.height <= 0.0f) return
        rotated(position) {
            scaled(it) { position ->
                val rad = bounds.height * 0.5f
                val collision = position - this.position
                if (collision.lengthSquared > (rad + radius) * (rad + radius)) {
                    return
                }
                this.position + collision.normalize(rad)
            }
        }.let(onCollision)
    }

    override fun draw(canvas: Canvas) {
        canvas
            .drawOval(bounds, fillPaint)
            .drawOval(bounds, strokePaint)
    }
}
