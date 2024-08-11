package me.altered.platformer.level.node

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.platformer.editor.toShader
import me.altered.platformer.engine.graphics.Paint
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.graphics.drawRRect
import me.altered.platformer.engine.graphics.offset
import me.altered.platformer.level.data.Rectangle
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.RRect

class RectangleNode(
    obj: Rectangle? = null,
    parent: Node? = null,
) : ObjectNode<Rectangle>(obj, parent) {

    private val fillPaint = Paint { mode = PaintMode.FILL }
    private val strokePaint = Paint { mode = PaintMode.STROKE }
    private var cornerRadius = 0.0f

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
        cornerRadius = obj.cornerRadius.eval(time)
        fillPaint.shader = obj.fill.eval(time).toShader()
        strokePaint.shader = obj.stroke.eval(time).toShader()
        strokePaint.strokeWidth = obj.strokeWidth.eval(time)
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

    override fun draw(canvas: Canvas) {
        val rect = RRect.makeLTRB(bounds.left, bounds.top, bounds.right, bounds.bottom, cornerRadius)
        canvas
            .drawRRect(rect, fillPaint)
            .drawRRect(rect, strokePaint)
    }
}
