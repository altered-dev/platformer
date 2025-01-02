package me.altered.platformer.level.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import me.altered.platformer.action.MutableAction
import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.expression.InspectorInfo

class MutableGroup(
    id: Long,
    name: String = "Group",
    override val x: AnimatedFloatState = AnimatedFloatState(0.0f, InspectorInfo.X),
    override val y: AnimatedFloatState = AnimatedFloatState(0.0f, InspectorInfo.Y),
    override val rotation: AnimatedFloatState = AnimatedFloatState(0.0f, InspectorInfo.Rotation),
    override val width: AnimatedFloatState = AnimatedFloatState(1.0f, InspectorInfo.Width),
    override val height: AnimatedFloatState = AnimatedFloatState(1.0f, InspectorInfo.Height),
    collisionFlags: CollisionFlags = CollisionFlags(false),
    isDamaging: Boolean = false,
    override val actions: SnapshotStateList<MutableAction> = mutableStateListOf(),
    override val children: SnapshotStateList<MutableObject> = mutableStateListOf(),
) : Group(id, name, x, y, rotation, width, height, collisionFlags, isDamaging),
    MutableObject,
    MutableObject.HasCollision,
    MutableObject.HasActions
{
    override var name by mutableStateOf(name)
    override var collisionFlags by mutableStateOf(collisionFlags)
    override var isDamaging by mutableStateOf(isDamaging)

    override fun toObject() = Group(
        id = id,
        name = name,
        x = x.toExpression(),
        y = y.toExpression(),
        rotation = rotation.toExpression(),
        width = width.toExpression(),
        height = height.toExpression(),
        collisionFlags = collisionFlags,
        isDamaging = isDamaging,
        actions = actions.map { it.toAction() },
        children = children.map { it.toObject() },
    )

    override fun toMutableObject() = this
}
