package me.altered.platformer.level.objects

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import me.altered.platformer.engine.geometry.div
import me.altered.platformer.engine.geometry.normalize
import me.altered.platformer.engine.geometry.rotateAround
import me.altered.platformer.engine.geometry.scale
import me.altered.platformer.engine.geometry.scaleAround
import me.altered.platformer.expression.Expression
import me.altered.platformer.expression.const
import me.altered.platformer.expression.toAnimatedBrushState
import me.altered.platformer.expression.toAnimatedFloatState
import me.altered.platformer.level.data.Brush
import me.altered.platformer.level.data.toComposeBrush
import androidx.compose.ui.graphics.Brush as ComposeBrush

@Serializable
open class Ellipse(
    override val id: Long,
    override val name: String = "Ellipse",
    override val x: Expression<Float> = const(0.0f),
    override val y: Expression<Float> = const(0.0f),
    override val rotation: Expression<Float> = const(0.0f),
    override val width: Expression<Float> = const(1.0f),
    override val height: Expression<Float> = const(1.0f),
    override val fill: List<Expression<Brush>> = emptyList(),
    override val stroke: List<Expression<Brush>> = emptyList(),
    override val strokeWidth: Expression<Float> = const(0.0f),
    override val collisionFlags: CollisionFlags = CollisionFlags(false),
    override val isDamaging: Boolean = false,
) : Object,
    Object.HasFill,
    Object.HasStroke,
    Object.HasCollision,
    Object.Drawable
{
    @Transient
    protected open var position = Offset.Zero
    @Transient
    protected open var _rotation = 0.0f
    @Transient
    protected open var bounds = Rect.Zero
    @Transient
    protected open var _fill = emptyList<ComposeBrush>()
    @Transient
    protected open var _stroke = emptyList<ComposeBrush>()
    @Transient
    protected open var _strokeWidth = 0.0f

    override fun DrawScope.draw() = withTransform({
        translate(position.x, position.y)
        rotate(_rotation, Offset.Zero)
    }) {
        _fill.forEach { brush ->
            drawOval(brush, bounds.topLeft, bounds.size)
        }
        _stroke.forEach { brush ->
            drawOval(brush, bounds.topLeft, bounds.size, style = Stroke(_strokeWidth))
        }
    }

    override fun collide(position: Offset, radius: Float): List<CollisionInfo> {
        if (bounds.isEmpty) return emptyList()
        rotated(position) {
            scaled(it) { position ->
                val rad = bounds.height * 0.5f
                val collision = position - this.position
                if (collision.getDistanceSquared() > (rad + radius) * (rad + radius)) {
                    return emptyList()
                }
                this.position + collision.normalize(rad)
            }
        }.let { return listOf(CollisionInfo(it, Offset.Unspecified)) }
    }

    // TODO: uncopypaste
    protected inline fun rotated(position: Offset, transform: (position: Offset) -> Offset): Offset {
        if (_rotation == 0.0f) return transform(position)
        val newPos = position.rotateAround(this.position, _rotation)
        return transform(newPos).rotateAround(this.position, -_rotation)
    }

    protected inline fun scaled(position: Offset, transform: (position: Offset) -> Offset): Offset {
        if (bounds.width == bounds.height) return transform(position)
        val scale = Size(bounds.height / bounds.width, 1.0f)
        val newPos = position.scaleAround(this.position, scale)
        return transform(newPos).scaleAround(this.position, 1.0f / scale)
    }

    override fun eval(time: Float) {
        position = Offset(x.eval(time), y.eval(time))
        _rotation = rotation.eval(time)
        bounds = Object.baseBounds.scale(width.eval(time), height.eval(time))
        _fill = fill.map { it.eval(time).toComposeBrush() }
        _stroke = stroke.map { it.eval(time).toComposeBrush() }
        _strokeWidth = strokeWidth.eval(time)
    }

    override fun toMutableObject() = MutableEllipse(
        id = id,
        name = name,
        x = x.toAnimatedFloatState(),
        y = y.toAnimatedFloatState(),
        rotation = rotation.toAnimatedFloatState(),
        width = width.toAnimatedFloatState(),
        height = height.toAnimatedFloatState(),
        fill = fill.mapTo(mutableStateListOf()) { it.toAnimatedBrushState() },
        stroke = stroke.mapTo(mutableStateListOf()) { it.toAnimatedBrushState() },
        strokeWidth = strokeWidth.toAnimatedFloatState(),
        collisionFlags = collisionFlags,
        isDamaging = isDamaging,
    )
}
