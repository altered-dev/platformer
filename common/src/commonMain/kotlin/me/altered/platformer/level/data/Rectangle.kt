package me.altered.platformer.level.data

import androidx.compose.runtime.mutableStateListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.expression.Expression
import me.altered.platformer.expression.const
import me.altered.platformer.expression.toAnimatedBrushState
import me.altered.platformer.expression.toAnimatedFloatState

@Serializable
@SerialName("rectangle")
open class Rectangle(
    override val id: Long,
    override val name: String = "Rectangle",
    override val x: Expression<Float> = const(0.0f),
    override val y: Expression<Float> = const(0.0f),
    override val rotation: Expression<Float> = const(0.0f),
    open val cornerRadius: Expression<Float> = const(0.0f),
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

    override fun toMutableObject() = MutableRectangle(
        id = id,
        name = name,
        x = x.toAnimatedFloatState(),
        y = y.toAnimatedFloatState(),
        rotation = rotation.toAnimatedFloatState(),
        cornerRadius = cornerRadius.toAnimatedFloatState(),
        width = width.toAnimatedFloatState(),
        height = height.toAnimatedFloatState(),
        fill = fill.mapTo(mutableStateListOf()) { it.toAnimatedBrushState() },
        stroke = stroke.mapTo(mutableStateListOf()) { it.toAnimatedBrushState() },
        strokeWidth = strokeWidth.toAnimatedFloatState(),
        collisionFlags = collisionFlags,
        isDamaging = isDamaging,
    )
}