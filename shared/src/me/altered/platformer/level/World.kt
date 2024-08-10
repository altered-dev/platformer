package me.altered.platformer.level

import me.altered.koml.Vector2fc
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.level.data.Object
import me.altered.platformer.level.node.ObjectNode

class World(
    name: String = "World",
    parent: Node? = null,
) : Node2D(name, parent), WorldContext {

    val objects = children.asSequence().filterIsInstance<ObjectNode<*>>()

    override var time: Float = 0.0f
        set(value) {
            if (field == value) return
            field = value
            objects.forEach { it.eval(value) }
        }

    override fun place(node: ObjectNode<*>) {
        addChild(node)
    }

    override fun remove(node: ObjectNode<*>) {
        removeChild(node)
    }

    override fun reference(vararg path: String): Object {
        TODO("reference class")
    }

    override fun ready() {
        objects.forEach { it.eval(time) }
    }

    fun collide(position: Vector2fc, radius: Float, onCollision: (point: Vector2fc) -> Unit) {
        objects.forEach { it.collide(position, radius, onCollision) }
    }
}
