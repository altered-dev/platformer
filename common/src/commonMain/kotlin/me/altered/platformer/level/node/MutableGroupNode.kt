package me.altered.platformer.level.node

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import me.altered.platformer.engine.geometry.scale
import me.altered.platformer.level.data.MutableGroup
import me.altered.platformer.level.data.Object
import kotlin.math.max
import kotlin.math.min

class MutableGroupNode(
    override val obj: MutableGroup,
    override var parent: MutableGroupNode? = null,
) : GroupNode(obj, parent), MutableObjectNode {

    override val children = obj.children.mapTo(mutableStateListOf()) { it.toMutableObjectNode(this) }

    override var position: Offset
        get() = Offset(obj.x.staticValue, obj.y.staticValue)
        set(_) = Unit
    override var rotation: Float
        get() = obj.rotation.staticValue
        set(_) = Unit
    override var bounds: Rect
        get() {
            if (children.isEmpty()) {
                return Object.baseBounds
                    .scale(scale.width, scale.height)
            }
            var left = Float.POSITIVE_INFINITY
            var top = Float.POSITIVE_INFINITY
            var right = Float.NEGATIVE_INFINITY
            var bottom = Float.NEGATIVE_INFINITY
            children.forEach { obj ->
                val bounds = obj.globalBounds
                    .scale(scale.width, scale.height)
                left = min(left, bounds.left)
                top = min(top, bounds.top)
                right = max(right, bounds.right)
                bottom = max(bottom, bounds.bottom)
            }
            val topLeft = Offset(left, top)
            val bottomRight = Offset(right, bottom)
            return Rect(topLeft, bottomRight)
                .translate(-position)
        }
        set(_) = Unit
    override var scale: Size
        get() = Size(obj.width.staticValue, obj.height.staticValue)
        set(_) = Unit

    /**
     * Adds a child and changes the parent of [node] to this node.
     */
    fun addChild(node: MutableObjectNode) {
        node.parent?.let { it.children -= node }
        node.parent = this
        children += node
    }

    /**
     * Adds [obj] as a child and returns a new node with the object.
     */
    fun addChild(obj: Object): MutableObjectNode {
        val node = obj.toMutableObjectNode(this)
        children += node
        return node
    }

    /**
     * Removes the child and sets the parent to null,
     * does nothing if the node's parent is not this node.
     */
    fun removeChild(node: MutableObjectNode): Boolean {
        if (node.parent != this) return false
        children -= node
        node.parent = null
        return true
    }

    override fun toMutableObjectNode() = this
}
