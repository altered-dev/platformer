package me.altered.platformer.level.node

import me.altered.koml.Vector2fc
import me.altered.platformer.engine.node.Node
import me.altered.platformer.level.ObjectContainer
import me.altered.platformer.level.data.Group

class GroupNode(
    obj: Group? = null,
    parent: Node? = null,
) : ObjectNode<Group>(obj, parent), ObjectContainer {

    val objects = children.asSequence().filterIsInstance<ObjectNode<*>>()

    override fun eval(time: Float) {
        val obj = obj ?: return
        position.set(obj.x.eval(time), obj.y.eval(time))
        rotation = obj.rotation.eval(time)
        scale.set(obj.width.eval(time), obj.height.eval(time))
        objects.forEach { it.eval(time) }
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
