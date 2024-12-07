package me.altered.platformer.level.objects

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import me.altered.platformer.expression.Expression
import me.altered.platformer.expression.const
import me.altered.platformer.expression.toAnimatedFloatState
import me.altered.platformer.level.data.CollisionFlags
import me.altered.platformer.level.data.CollisionInfo

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
    Object.HasCollision,
    Object.Drawable,
    Object.EditorDrawable
{
    @Transient
    protected open var position = Offset.Zero
    @Transient
    protected open var _rotation = 0.0f
    @Transient
    protected open var scale = Size.Zero

    override fun DrawScope.draw() {
        withTransform({
            translate(position.x, position.y)
            rotate(_rotation, Offset.Zero)
            scale(scale.width, scale.height, Offset.Zero)
        }) {
            children.forEach {
                if (it is Object.Drawable) it.draw(this)
            }
        }
    }

    override fun DrawScope.drawInEditor() {
        withTransform({
            translate(position.x, position.y)
            rotate(_rotation, Offset.Zero)
        }) {
            drawCircle(Color.White, 0.05f, Offset.Zero, blendMode = BlendMode.Exclusion)
            drawLine(Color.White, Offset(scale.width / -2, 0.0f), Offset(-0.1f, 0.0f), blendMode = BlendMode.Exclusion)
            drawLine(Color.White, Offset(0.1f, 0.0f), Offset(scale.width / 2, 0.0f), blendMode = BlendMode.Exclusion)
            drawLine(Color.White, Offset(0.0f, scale.height / -2), Offset(0.0f, -0.1f), blendMode = BlendMode.Exclusion)
            drawLine(Color.White, Offset(0.0f, 0.1f), Offset(0.0f, scale.height / 2), blendMode = BlendMode.Exclusion)
            scale(scale.width, scale.height, Offset.Zero) {
                children.forEach {
                    if (it is Object.EditorDrawable) it.drawInEditor(this)
                }
            }
        }
    }

    override fun collide(position: Offset, radius: Float): List<CollisionInfo> {
        // FIXME: does not account for rotation and scale yet
        return children.flatMap {
            if (it is Object.HasCollision) {
                it.collide(position - this.position, radius).map { info ->
                    info.copy(point = info.point + this.position)
                }
            } else emptyList()
        }
    }

    override fun eval(time: Float) {
        position = Offset(x.eval(time), y.eval(time))
        _rotation = rotation.eval(time)
        scale = Size(width.eval(time), height.eval(time))
        children.forEach { it.eval(time) }
    }

    override fun toMutableObject() = MutableGroup(
        id = id,
        name = name,
        x = x.toAnimatedFloatState(),
        y = y.toAnimatedFloatState(),
        rotation = rotation.toAnimatedFloatState(),
        width = width.toAnimatedFloatState(),
        height = height.toAnimatedFloatState(),
        collisionFlags = collisionFlags,
        isDamaging = isDamaging,
        children = children.mapTo(mutableStateListOf()) { it.toMutableObject() },
    )
}
