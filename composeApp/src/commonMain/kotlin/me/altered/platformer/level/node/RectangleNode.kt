package me.altered.platformer.level.node

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import me.altered.platformer.engine.geometry.div
import me.altered.platformer.engine.geometry.rotateAround
import me.altered.platformer.engine.geometry.scale
import me.altered.platformer.engine.geometry.scaleAround
import me.altered.platformer.level.data.CollisionFlags
import me.altered.platformer.level.data.CollisionInfo
import me.altered.platformer.level.data.toComposeBrush
import me.altered.platformer.level.objects.Object
import me.altered.platformer.level.objects.Rectangle

open class RectangleNode(
    override val obj: Rectangle,
    override val parent: GroupNode? = null,
) : ObjectNode,
    ObjectNode.HasFill,
    ObjectNode.HasStroke,
    ObjectNode.HasCollision,
    ObjectNode.Drawable
{
    override var position = Offset.Zero
        protected set
    override var rotation = 0.0f
        protected set
    override var bounds = Rect.Zero
        protected set
    override var fill = emptyList<Brush>()
        protected set
    override var stroke = emptyList<Brush>()
        protected set
    override var strokeWidth = 0.0f
        protected set
    override var collisionFlags = CollisionFlags(false)
        protected set
    override var isDamaging = false
        protected set
    open var cornerRadius = 0.0f
        protected set

    override fun eval(time: Float) {
        position = Offset(obj.x.eval(time), obj.y.eval(time))
        rotation = obj.rotation.eval(time)
        cornerRadius = obj.cornerRadius.eval(time)
        bounds = Object.baseBounds.scale(obj.width.eval(time), obj.height.eval(time))
        fill = obj.fill.map { it.eval(time).toComposeBrush() }
        stroke = obj.stroke.map { it.eval(time).toComposeBrush() }
        strokeWidth = obj.strokeWidth.eval(time)
    }

    override fun DrawScope.draw() = withTransform({
        translate(position.x, position.y)
        rotate(rotation, Offset.Zero)
    }) {
        fill.forEach { brush ->
            drawRoundRect(brush, bounds.topLeft, bounds.size, CornerRadius(cornerRadius))
        }
        stroke.forEach { brush ->
            drawRoundRect(brush, bounds.topLeft, bounds.size, CornerRadius(cornerRadius), style = Stroke(strokeWidth))
        }
    }

    override fun collide(position: Offset, radius: Float): List<CollisionInfo> {
        if (bounds.isEmpty) return emptyList()
        rotated(position) { position ->
            val bounds = bounds.translate(this.position)
            val x = position.x.coerceIn(bounds.left, bounds.right)
            val y = position.y.coerceIn(bounds.top, bounds.bottom)
            val vec = Offset(x, y)
            if ((position - vec).getDistanceSquared() > (radius * radius)) {
                return emptyList()
            }
            vec
        }.let { return listOf(CollisionInfo(it, Offset.Unspecified)) }
    }

    // TODO: uncopypaste
    protected inline fun rotated(position: Offset, transform: (position: Offset) -> Offset): Offset {
        if (rotation == 0.0f) return transform(position)
        val newPos = position.rotateAround(this.position, rotation)
        return transform(newPos).rotateAround(this.position, -rotation)
    }

    protected inline fun scaled(position: Offset, transform: (position: Offset) -> Offset): Offset {
        if (bounds.width == bounds.height) return transform(position)
        val scale = Size(bounds.height / bounds.width, 1.0f)
        val newPos = position.scaleAround(this.position, scale)
        return transform(newPos).scaleAround(this.position, 1.0f / scale)
    }

    override fun toMutableObjectNode() = MutableRectangleNode(obj.toMutableObject())
}
