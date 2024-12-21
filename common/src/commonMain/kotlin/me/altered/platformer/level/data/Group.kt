package me.altered.platformer.level.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
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
    open val children: List<Object> = emptyList(),
) : Object,
    Object.HasCollision
{
    @Transient
    protected open var position = Offset.Zero
    @Transient
    protected open var _rotation = 0.0f
    @Transient
    protected open var scale = Size.Zero


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
        children = children.mapTo(mutableStateListOf()) { it.toMutableObject() },
    )
}
