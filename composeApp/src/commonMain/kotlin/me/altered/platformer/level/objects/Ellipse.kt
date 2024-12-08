package me.altered.platformer.level.objects

import androidx.compose.runtime.mutableStateListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.expression.Expression
import me.altered.platformer.expression.const
import me.altered.platformer.expression.toAnimatedBrushState
import me.altered.platformer.expression.toAnimatedFloatState
import me.altered.platformer.level.data.Brush
import me.altered.platformer.level.data.CollisionFlags

@Serializable
@SerialName("ellipse")
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
    Object.HasCollision
{

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
