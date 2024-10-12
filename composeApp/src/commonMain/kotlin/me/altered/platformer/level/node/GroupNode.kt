package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import me.altered.platformer.level.ObjectContainer
import me.altered.platformer.level.TimeContext
import me.altered.platformer.level.data.Group
import me.altered.platformer.node.Node

class GroupNode(
    obj: Group? = null,
    parent: Node? = null,
) : ObjectNode<Group>(obj, parent), ObjectContainer {

    val objects = children.asSequence().filterIsInstance<ObjectNode<*>>()

    override fun TimeContext.eval() {
        val obj = obj ?: return
        position = Offset(obj.x.value, obj.y.value)
        rotation = obj.rotation.value
        scale = Size(obj.width.value, obj.height.value)
        objects.forEach { it.eval(this) }
    }

    override fun collide(position: Offset, radius: Float, onCollision: (point: Offset) -> Unit) {
        objects.forEach { it.collide(position, radius, onCollision) }
    }

    override fun place(node: ObjectNode<*>) {
        addChild(node)
    }

    override fun remove(node: ObjectNode<*>) {
        removeChild(node)
    }
}
