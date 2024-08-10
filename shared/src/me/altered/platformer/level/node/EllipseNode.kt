package me.altered.platformer.level.node

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.koml.div
import me.altered.koml.scaleAround
import me.altered.platformer.editor.toShader
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.util.Paint
import me.altered.platformer.level.data.Ellipse
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode

class EllipseNode(
    obj: Ellipse? = null,
    parent: Node? = null,
) : ObjectNode<Ellipse>(obj, parent) {

    private val fillPaint = Paint { mode = PaintMode.FILL }
    private val strokePaint = Paint { mode = PaintMode.STROKE }

    override fun eval(time: Float) {
        val obj = obj ?: return
        position.set(
            x = obj.x.eval(time),
            y = obj.y.eval(time),
        )
        rotation = obj.rotation.eval(time)
        bounds = baseBounds.scale(
            sx = obj.width.eval(time),
            sy = obj.height.eval(time),
        )
        fillPaint.shader = obj.fill.eval(time).toShader()
        strokePaint.shader = obj.stroke.eval(time).toShader()
        strokePaint.strokeWidth = obj.strokeWidth.eval(time)
    }

    override fun collide(position: Vector2fc, radius: Float, onCollision: (point: Vector2fc) -> Unit) {
        if (bounds.width <= 0.0f || bounds.height <= 0.0f) return
        return rotated(position) {
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

    private inline fun scaled(position: Vector2fc, transform: (position: Vector2fc) -> Vector2fc): Vector2fc {
        if (bounds.width == bounds.height) return transform(position)
        val diff = bounds.height / bounds.width
        val scale = Vector2f(diff, 1.0f)
        val newPos = position.scaleAround(this.position, scale)
        val result = transform(newPos).scaleAround(this.position, 1.0f / scale)
        return result
    }

    override fun draw(canvas: Canvas) {
        canvas
            .drawOval(bounds, fillPaint)
            .drawOval(bounds, strokePaint)
    }
}
