package me.altered.platformer.level.node

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import me.altered.platformer.action.Effect
import me.altered.platformer.action.FireTrigger
import me.altered.platformer.action.MoveBy
import me.altered.platformer.action.PlayerCollided
import me.altered.platformer.action.RotateBy
import me.altered.platformer.engine.geometry.div
import me.altered.platformer.engine.geometry.rotateAround
import me.altered.platformer.engine.geometry.scale
import me.altered.platformer.engine.geometry.scaleAround
import me.altered.platformer.level.data.CollisionInfo
import me.altered.platformer.level.data.FloatProperty
import me.altered.platformer.level.data.toComposeBrush
import me.altered.platformer.level.data.Object
import me.altered.platformer.level.data.OffsetProperty
import me.altered.platformer.level.data.Rectangle

open class RectangleNode(
    override val obj: Rectangle,
    override val parent: GroupNode? = null,
) : ObjectNode,
    ObjectNode.HasCornerRadius,
    ObjectNode.HasFill,
    ObjectNode.HasStroke,
    ObjectNode.HasCollision,
    ObjectNode.Drawable,
    ObjectNode.HasActions
{
    // TODO: remove
    private var time = 0.0f

    private val _position = OffsetProperty(obj.x, obj.y)
    private val _rotation = FloatProperty(obj.rotation)

    override val position by _position
    override val rotation by _rotation
    override var bounds = Rect.Zero
        protected set
    override var cornerRadius = 0.0f
        protected set
    override var fill = emptyList<Brush>()
        protected set
    override var stroke = emptyList<Brush>()
        protected set
    override var strokeWidth = 0.0f
        protected set
    override val collisionFlags get() = obj.collisionFlags
    override val isDamaging get() = obj.isDamaging
    override val actions get() = obj.actions

    override fun eval(time: Float) {
        this.time = time
        _position.eval(time)
        _rotation.eval(time)
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

    override fun fireAction(trigger: FireTrigger) {
        val action = actions.find { it.trigger matches trigger }
            ?: return
        action.effects.forEach { effect ->
            when (effect) {
                is MoveBy -> _position.inject(effect, time)
                is RotateBy -> _rotation.inject(effect, time)
            }
        }
    }

    private var isColliding = false

    override fun collide(position: Offset, radius: Float): List<CollisionInfo> {
        if (bounds.isEmpty) {
            isColliding = false
            return emptyList()
        }
        rotated(position) { position ->
            val bounds = bounds.translate(this.position)
            val x = position.x.coerceIn(bounds.left, bounds.right)
            val y = position.y.coerceIn(bounds.top, bounds.bottom)
            val vec = Offset(x, y)
            if ((position - vec).getDistanceSquared() > (radius * radius)) {
                isColliding = false
                return emptyList()
            }
            vec
        }.let {
            if (!isColliding) {
                isColliding = true
                fireAction(PlayerCollided)
            }
            return listOf(CollisionInfo(it, Offset.Unspecified))
        }
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
