package me.altered.platformer.level.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import me.altered.platformer.expression.AnimatedBrushState
import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.expression.InspectorInfo

class MutableEllipse(
    id: Long,
    name: String = "Rectangle",
    override val x: AnimatedFloatState = AnimatedFloatState(0.0f, InspectorInfo.X),
    override val y: AnimatedFloatState = AnimatedFloatState(0.0f, InspectorInfo.Y),
    override val rotation: AnimatedFloatState = AnimatedFloatState(0.0f, InspectorInfo.Rotation),
    override val width: AnimatedFloatState = AnimatedFloatState(1.0f, InspectorInfo.Width),
    override val height: AnimatedFloatState = AnimatedFloatState(1.0f, InspectorInfo.Height),
    override val fill: MutableList<AnimatedBrushState> = mutableStateListOf(),
    override val stroke: MutableList<AnimatedBrushState> = mutableStateListOf(),
    override val strokeWidth: AnimatedFloatState = AnimatedFloatState(0.0f),
    collisionFlags: CollisionFlags = CollisionFlags(false),
    isDamaging: Boolean = false,
) : Ellipse(id, name, x, y, rotation, width, height, fill, stroke, strokeWidth, collisionFlags, isDamaging),
    MutableObject,
    MutableObject.HasFill,
    MutableObject.HasStroke,
    MutableObject.HasCollision
{
    override var name by mutableStateOf(name)
    override var collisionFlags by mutableStateOf(collisionFlags)
    override var isDamaging by mutableStateOf(isDamaging)

    override fun toObject() = Ellipse(
        id = id,
        name = name,
        x = x.toExpression(),
        y = y.toExpression(),
        rotation = rotation.toExpression(),
        width = width.toExpression(),
        height = height.toExpression(),
        fill = fill.map { it.toExpression() },
        stroke = stroke.map { it.toExpression() },
        strokeWidth = strokeWidth.toExpression(),
        collisionFlags = collisionFlags,
        isDamaging = isDamaging,
    )

    override fun toMutableObject() = this
}
