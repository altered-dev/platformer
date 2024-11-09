package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import me.altered.platformer.engine.geometry.div
import me.altered.platformer.engine.geometry.rotateAround
import me.altered.platformer.engine.geometry.scaleAround
import me.altered.platformer.level.TimeContext
import me.altered.platformer.level.data.Object
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.Node2D
import kotlin.jvm.JvmStatic

sealed class ObjectNode<O : Object>(
    open var obj: O? = null,
    parent: Node? = null,
    val id: ULong = globalId++,
) : Node2D(obj?.name ?: "ObjectNode", parent) {

    var bounds = baseBounds

    val globalBounds: Rect
        get() = bounds.translate(globalPosition)

    /**
     * Evaluates the underlying object and applies the results to the node.
     * Must not be called before [ready], otherwise might yield unexpected results or failures.
     */
    abstract fun TimeContext.eval()

    abstract fun collide(position: Offset, radius: Float, onCollision: (point: Offset) -> Unit)

    protected inline fun rotated(position: Offset, transform: (position: Offset) -> Offset): Offset {
        if (rotation == 0.0f) return transform(position)
        val newPos = position.rotateAround(this.position, rotation)
        return transform(newPos).rotateAround(this.position, -rotation)
    }

    protected inline fun scaled(position: Offset, transform: (position: Offset) -> Offset): Offset {
        if (bounds.width == bounds.height) return transform(position)
        val scale = Size(bounds.height / bounds.width, 1.0f)
        val newPos = position.scaleAround(this.position, scale)
        return transform(newPos).scaleAround(this.position, 1.0f / scale)
    }

    override fun toString(): String = "${super.toString()} $obj"

    interface Filled {

        var fill: Brush
    }

    interface Stroked {

        var stroke: Brush
        var strokeWidth: Float
    }

    companion object {
        @JvmStatic
        val baseBounds = Rect(-0.5f, -0.5f, 0.5f, 0.5f)

        // TODO: replace with a better system
        private var globalId = 0UL
    }
}

fun ObjectNode<*>.eval(timeContext: TimeContext) {
    timeContext.run { eval() }
}
