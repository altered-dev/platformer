package me.altered.platformer.level.objects

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import me.altered.platformer.engine.geometry.scale
import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.level.data.CollisionFlags
import kotlin.math.max
import kotlin.math.min

@Suppress("SuspiciousVarProperty")
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

    override val globalBounds: Rect get() {
        if (children.isEmpty()) {
            return Object.baseBounds
                .translate(position)
                .scale(scale.width, scale.height)
        }
        var left = Float.POSITIVE_INFINITY
        var top = Float.POSITIVE_INFINITY
        var right = Float.NEGATIVE_INFINITY
        var bottom = Float.NEGATIVE_INFINITY
        children.forEach { obj ->
            val bounds = obj.globalBounds
                .scale(scale.width, scale.height)
            left = min(left, bounds.left)
            top = min(top, bounds.top)
            right = max(right, bounds.right)
            bottom = max(bottom, bounds.bottom)
        }
        val topLeft = Offset(left, top)
        val bottomRight = Offset(right, bottom)
        return Rect(topLeft, bottomRight)
            .translate(position)
    }

    override var position = super.position
        get() = Offset(x.staticValue, y.staticValue)
    override var _rotation = super._rotation
        get() = rotation.staticValue
    override var scale = super.scale
        get() = Size(width.staticValue, height.staticValue)

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
