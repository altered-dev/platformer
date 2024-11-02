package me.altered.platformer.level

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.drawscope.DrawTransform
import me.altered.platformer.level.data.Object
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.level.node.eval
import me.altered.platformer.node.Node

class World(
    name: String = "World",
    parent: Node? = null,
    var scale: Float = 1.0f,
    var position: Offset = Offset.Zero,
) : Node(name, parent), WorldContext {

    var DrawTransform.scale: Float
        get() = scale(size)
        set(value) {
            this@World.scale = value * 20.0f / size.height
        }

    val DrawTransform.offset: Offset
        get() = offset(size)

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
        objects.forEach { println("ready $it"); it.eval(this) }
    }

    fun collide(position: Offset, radius: Float, onCollision: (point: Offset) -> Unit) {
        objects.forEach { it.collide(position, radius, onCollision) }
    }

    fun scale(size: Size): Float {
        return scale * size.height * 0.05f
    }

    fun offset(size: Size): Offset {
        return position + size.center
    }

    fun screenToWorld(vec: Offset, size: Size): Offset {
        return (vec - offset(size)) / scale(size)
    }

    fun worldToScreen(vec: Offset, size: Size): Offset {
        return vec * scale(size) + offset(size)
    }

    fun screenToWorld(rect: Rect, size: Size): Rect {
        val offset = offset(size)
        val scale = scale(size)
        return Rect(
            left = (rect.left - offset.x) / scale,
            top = (rect.top - offset.y) / scale,
            right = (rect.right - offset.x) / scale,
            bottom = (rect.bottom - offset.y) / scale,
        )
    }

    fun worldToScreen(rect: Rect, size: Size): Rect {
        val offset = offset(size)
        val scale = scale(size)
        return Rect(
            left = rect.left * scale + offset.x,
            top = rect.top * scale + offset.y,
            right = rect.right * scale + offset.x,
            bottom = rect.bottom * scale + offset.y,
        )
    }

    fun DrawTransform.screenToWorld(vec: Offset): Offset {
        return screenToWorld(vec, size)
    }

    fun DrawTransform.worldToScreen(vec: Offset): Offset {
        return worldToScreen(vec, size)
    }
}
