package me.altered.platformer.level.data

import androidx.compose.runtime.mutableStateListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.action.Action
import me.altered.platformer.expression.Expression
import me.altered.platformer.expression.InspectorInfo
import me.altered.platformer.expression.const
import me.altered.platformer.expression.toAnimatedFloatState

@Serializable
@SerialName("group")
open class Group(
    override val id: Long,
    override val name: String = "Group",
    override val x: Expression<Float> = const(0.0f),
    override val y: Expression<Float> = const(0.0f),
    override val rotation: Expression<Float> = const(0.0f),
    override val width: Expression<Float> = const(1.0f),
    override val height: Expression<Float> = const(1.0f),
    override val collisionFlags: CollisionFlags = CollisionFlags(false),
    override val isDamaging: Boolean = false,
    override val actions: List<Action> = emptyList(),
    open val children: List<Object> = emptyList(),
) : Object,
    Object.HasCollision,
    Object.HasActions
{

    override fun toMutableObject() = MutableGroup(
        id = id,
        name = name,
        x = x.toAnimatedFloatState(InspectorInfo.X),
        y = y.toAnimatedFloatState(InspectorInfo.Y),
        rotation = rotation.toAnimatedFloatState(InspectorInfo.Rotation),
        width = width.toAnimatedFloatState(InspectorInfo.Width),
        height = height.toAnimatedFloatState(InspectorInfo.Height),
        collisionFlags = collisionFlags,
        isDamaging = isDamaging,
        actions = actions.mapTo(mutableStateListOf()) { it.toMutableAction() },
        children = children.mapTo(mutableStateListOf()) { it.toMutableObject() },
    )
}
