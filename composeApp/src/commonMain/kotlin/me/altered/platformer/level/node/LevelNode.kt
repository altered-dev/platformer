package me.altered.platformer.level.node

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.transform
import me.altered.platformer.level.ObjectContainer
import me.altered.platformer.level.TimeContext

open class LevelNode(
    name: String = "Level",
    parent: Node? = null,
    // not a very good idea but ok
    val objects: MutableList<ObjectNode<*>>,
) : Node(name, parent), ObjectContainer, TimeContext {

    override val children: List<Node>
        get() = objects

    override var time: Float = 0.0f
        set(value) {
            if (field == value) return
            field = value
            objects.forEach { it.eval(this) }
        }

    override fun ready() {
        super.ready()
        objects.forEach { it.eval(this) }
    }

    override fun place(node: ObjectNode<*>) {
        objects += node
        node.eval(this)
    }

    override fun remove(node: ObjectNode<*>) {
        objects -= node
    }
}
