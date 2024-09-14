package me.altered.platformer.level.node

import me.altered.koml.Vector2fc
import me.altered.platformer.engine.node.Node
import me.altered.platformer.level.ObjectContainer
import me.altered.platformer.level.TimeContext
import me.altered.platformer.level.data.Group

class GroupNode(
    obj: Group? = null,
    parent: Node? = null,
) : ObjectNode<Group>(obj, parent), ObjectContainer {

    val objects = children.asSequence().filterIsInstance<ObjectNode<*>>()

    override fun TimeContext.eval() {
        val obj = obj ?: return
        position.set(obj.x.value, obj.y.value)
        rotation = obj.rotation.value
        scale.set(obj.width.value, obj.height.value)
        objects.forEach { it.eval(this) }
    }

    override fun collide(position: Vector2fc, radius: Float, onCollision: (point: Vector2fc) -> Unit) {
        objects.forEach { it.collide(position, radius, onCollision) }
    }

    override fun place(node: ObjectNode<*>) {
        addChild(node)
    }

    override fun remove(node: ObjectNode<*>) {
        removeChild(node)
    }
}
