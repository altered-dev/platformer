package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import me.altered.platformer.action.Action
import me.altered.platformer.action.FireTrigger
import me.altered.platformer.action.PlayerCollided
import me.altered.platformer.action.effect.MoveBy
import me.altered.platformer.action.effect.MutableMoveBy
import me.altered.platformer.action.effect.MutableRotateBy
import me.altered.platformer.action.effect.RotateBy
import me.altered.platformer.engine.geometry.div
import me.altered.platformer.engine.geometry.normalize
import me.altered.platformer.engine.geometry.rotateAround
import me.altered.platformer.engine.geometry.scale
import me.altered.platformer.engine.geometry.scaleAround
import me.altered.platformer.level.data.CollisionFlags
import me.altered.platformer.level.data.CollisionInfo
import me.altered.platformer.level.data.toComposeBrush
import me.altered.platformer.level.data.Ellipse
import me.altered.platformer.level.data.FloatProperty
import me.altered.platformer.level.data.Object
import me.altered.platformer.level.data.OffsetProperty

@Suppress("CanBePrimaryConstructorProperty")
open class EllipseNode(
    obj: Ellipse,
    parent: GroupNode? = null,
) : ObjectNode,
    ObjectNode.HasFill,
    ObjectNode.HasStroke,
    ObjectNode.HasCollision,
    ObjectNode.Drawable,
    ObjectNode.HasActions
{
    override val obj = obj
    override val parent = parent

    // TODO: remove
    private var time = 0.0f

    private val _position = OffsetProperty(obj.x, obj.y)
    private val _rotation = FloatProperty(obj.rotation)

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
    override val collisionFlags: CollisionFlags
        get() = obj.collisionFlags
    override val isDamaging: Boolean
        get() = obj.isDamaging
    override val actions: List<Action>
        get() = obj.actions

    override fun eval(time: Float) {
        this.time = time
        position = _position.eval(time)
        rotation = _rotation.eval(time)
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
            drawOval(brush, bounds.topLeft, bounds.size)
        }
        stroke.forEach { brush ->
            drawOval(brush, bounds.topLeft, bounds.size, style = Stroke(strokeWidth))
        }
    }

    override fun fireAction(trigger: FireTrigger) {
        val action = actions.find { it.trigger matches trigger }
            ?: return
        action.effects.forEach { effect ->
            when (effect) {
                is MoveBy -> _position.inject(effect, time)
                is RotateBy -> _rotation.inject(effect, time)
                is MutableMoveBy -> _position.inject(effect, time)
                is MutableRotateBy -> _rotation.inject(effect, time)
            }
        }
    }

    private var isColliding = false

    override fun collide(position: Offset, radius: Float): List<CollisionInfo> {
        if (bounds.isEmpty) {
            isColliding = false
            return emptyList()
        }
        rotated(position) {
            scaled(it) { position ->
                val rad = bounds.height * 0.5f
                val collision = position - this.position
                if (collision.getDistanceSquared() > (rad + radius) * (rad + radius)) {
                    isColliding = false
                    return emptyList()
                }
                this.position + collision.normalize(rad)
            }
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

    override fun toMutableObjectNode() = MutableEllipseNode(obj.toMutableObject())
}
