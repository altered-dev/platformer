package me.altered.platformer.level.objects

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.level.data.CollisionFlags

class MutableGroup(
    id: Long,
    name: String = "Group",
    override val x: AnimatedFloatState = AnimatedFloatState(0.0f),
    override val y: AnimatedFloatState = AnimatedFloatState(0.0f),
    override val rotation: AnimatedFloatState = AnimatedFloatState(0.0f),
    override val width: AnimatedFloatState = AnimatedFloatState(1.0f),
    override val height: AnimatedFloatState = AnimatedFloatState(1.0f),
    collisionFlags: CollisionFlags = CollisionFlags(false),
    isDamaging: Boolean = false,
    override val children: MutableList<MutableObject> = mutableStateListOf(),
) : Group(id, name, x, y, rotation, width, height, collisionFlags, isDamaging),
    MutableObject,
    MutableObject.HasCollision
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
        children = children.map { it.toObject() },
    )

    override fun toMutableObject() = this
}