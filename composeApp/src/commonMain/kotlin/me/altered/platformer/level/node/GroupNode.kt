package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import me.altered.platformer.level.data.CollisionFlags
import me.altered.platformer.level.data.CollisionInfo
import me.altered.platformer.level.objects.Group
import me.altered.platformer.level.objects.draw
import me.altered.platformer.level.objects.drawInEditor

open class GroupNode(
    override val obj: Group,
    override val parent: GroupNode? = null,
) : ObjectNode,
    ObjectNode.HasCollision,
    ObjectNode.EditorDrawable,
    ObjectNode.Drawable
{
    open val children: List<ObjectNode> = if (this is MutableGroupNode) {
        // for some reason obj is null here
        emptyList()
    } else {
        obj.children.map { it.toObjectNode(this) }
    }

    override var position = Offset.Zero
        protected set
    override var rotation = 0.0f
        protected set
    // not calculated for group as it's only useful in the editor
    override var bounds = Rect.Zero
        protected set
    open var scale = Size.Zero
        protected set

    override val collisionFlags: CollisionFlags
        get() = obj.collisionFlags

    override val isDamaging: Boolean
        get() = obj.isDamaging

    override fun eval(time: Float) {
        position = Offset(obj.x.eval(time), obj.y.eval(time))
        rotation = obj.rotation.eval(time)
        scale = Size(obj.width.eval(time), obj.height.eval(time))
        children.forEach { it.eval(time) }
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

    override fun collide(position: Offset, radius: Float): List<CollisionInfo> {
        // FIXME: does not account for rotation and scale yet
        return children.flatMap {
            if (it is ObjectNode.HasCollision) {
                it.collide(position - this.position, radius).map { info ->
                    info.copy(point = info.point + this.position)
                }
            } else emptyList()
        }
    }

    override fun toMutableObjectNode() = MutableGroupNode(obj.toMutableObject())
}
