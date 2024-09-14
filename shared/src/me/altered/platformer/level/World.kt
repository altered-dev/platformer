package me.altered.platformer.level

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.Viewport
import me.altered.platformer.level.data.Object
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.level.node.eval

class World(
    name: String = "World",
    parent: Node? = null,
    var scale: Float = 1.0f,
    val position: Vector2f = Vector2f(),
) : Viewport(name, parent), WorldContext {

    override var size: Float
        get() = scale * (window?.height?.toFloat() ?: 1.0f) * 0.05f
        set(value) {
            scale = value * 20.0f / (window?.height?.toFloat() ?: 1.0f)
        }

    override val offset: Vector2f
        get() = window?.let { window ->
            position + Vector2f(window.width * 0.5f, window.height * 0.5f)
        } as? Vector2f ?: position

    val objects = children.asSequence().filterIsInstance<ObjectNode<*>>()

    override var time: Float = 0.0f
        set(value) {
            if (field == value) return
            field = value
            objects.forEach { it.eval(this) }
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
        objects.forEach { it.eval(this) }
    }

    fun collide(position: Vector2fc, radius: Float, onCollision: (point: Vector2fc) -> Unit) {
        objects.forEach { it.collide(position, radius, onCollision) }
    }
}
