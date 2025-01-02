package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import me.altered.platformer.action.FireTrigger
import me.altered.platformer.action.PlayerCollided
import me.altered.platformer.action.effect.MoveBy
import me.altered.platformer.action.effect.MutableMoveBy
import me.altered.platformer.action.effect.MutableRotateBy
import me.altered.platformer.action.effect.RotateBy
import me.altered.platformer.level.data.CollisionInfo
import me.altered.platformer.level.data.FloatProperty
import me.altered.platformer.level.data.Group
import me.altered.platformer.level.data.OffsetProperty
import me.altered.platformer.level.data.draw
import me.altered.platformer.level.data.drawInEditor

open class GroupNode(
    obj: Group,
    parent: GroupNode? = null,
) : ObjectNode,
    ObjectNode.HasCollision,
    ObjectNode.EditorDrawable,
    ObjectNode.Drawable,
    ObjectNode.HasActions
{
    override val obj = obj
    override val parent = parent

    // TODO: remove
    private var time = 0.0f

    open val children: List<ObjectNode> = obj.children.map { it.toObjectNode(this) }

    private val _position = OffsetProperty(obj.x, obj.y)
    private val _rotation = FloatProperty(obj.rotation)

    override var position = Offset.Zero
        protected set
    override var rotation = 0.0f
        protected set
    // not calculated for group as it's only useful in the editor
    override var bounds = Rect.Zero
        protected set
    open var scale = Size.Zero
        protected set

    override val collisionFlags get() = obj.collisionFlags
    override val isDamaging get() = obj.isDamaging
    override val actions get() = obj.actions

    override fun eval(time: Float) {
        position = _position.eval(time)
        rotation = _rotation.eval(time)
        scale = Size(obj.width.eval(time), obj.height.eval(time))
        children.forEach { it.eval(time) }
    }

    override fun fireAction(trigger: FireTrigger) {
        val action = actions.find { it.trigger matches trigger }
            ?: return
        action.effects.forEach { effect ->
            when (effect) {
                is MoveBy -> _position.inject(effect, time)
                is RotateBy -> _rotation.inject(effect, time)
                is MutableMoveBy -> _position.inject(effect, time)
                is MutableRotateBy -> _rotation.inject(effect, time)
            }
        }
    }

    override fun DrawScope.draw() {
        withTransform({
            translate(position.x, position.y)
            rotate(rotation, Offset.Zero)
            scale(scale.width, scale.height, Offset.Zero)
        }) {
            children.forEach {
                if (it is ObjectNode.Drawable) it.draw(this)
            }
        }
    }

    override fun DrawScope.drawInEditor() {
        withTransform({
            translate(position.x, position.y)
            rotate(rotation, Offset.Zero)
        }) {
            drawCircle(Color.White, 0.05f, Offset.Zero, blendMode = BlendMode.Exclusion)
            drawLine(Color.White, Offset(scale.width / -2, 0.0f), Offset(-0.1f, 0.0f), blendMode = BlendMode.Exclusion)
            drawLine(Color.White, Offset(0.1f, 0.0f), Offset(scale.width / 2, 0.0f), blendMode = BlendMode.Exclusion)
            drawLine(Color.White, Offset(0.0f, scale.height / -2), Offset(0.0f, -0.1f), blendMode = BlendMode.Exclusion)
            drawLine(Color.White, Offset(0.0f, 0.1f), Offset(0.0f, scale.height / 2), blendMode = BlendMode.Exclusion)
            scale(scale.width, scale.height, Offset.Zero) {
                children.forEach {
                    if (it is ObjectNode.EditorDrawable) it.drawInEditor(this)
                }
            }
        }
    }

    private var isColliding = false

    override fun collide(position: Offset, radius: Float): List<CollisionInfo> {
        // FIXME: does not account for rotation and scale yet
        return children.flatMap {
            if (it is ObjectNode.HasCollision) {
                it.collide(position - this.position, radius).map { info ->
                    info.copy(point = info.point + this.position)
                }
            } else emptyList()
        }.let {
            if (it.isNotEmpty() && !isColliding) {
                isColliding = true
                fireAction(PlayerCollided)
            } else if (it.isEmpty()) {
                isColliding = false
            }
            it
        }
    }

    override fun toMutableObjectNode() = MutableGroupNode(obj.toMutableObject())
}
