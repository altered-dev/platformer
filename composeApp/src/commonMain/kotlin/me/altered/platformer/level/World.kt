package me.altered.platformer.level

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawTransform
import me.altered.platformer.level.data.Object
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.level.node.eval
import me.altered.platformer.node.Node

class World(
    name: String = "World",
    parent: Node? = null,
    var scale: Float = 1.0f,
    val position: Offset = Offset.Zero,
) : Node(name, parent), WorldContext {

    var DrawTransform.scale: Float
        get() = this@World.scale * size.height * 0.05f
        set(value) {
            this@World.scale = value * 20.0f / size.height
        }

    val DrawTransform.offset: Offset
        get() = position + Offset(size.width / 2, size.height / 2)

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

    fun collide(position: Offset, radius: Float, onCollision: (point: Offset) -> Unit) {
        objects.forEach { it.collide(position, radius, onCollision) }
    }
}
