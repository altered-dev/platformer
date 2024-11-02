package me.altered.platformer.level.node

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.koml.div
import me.altered.koml.rotateAround
import me.altered.koml.scaleAround
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.level.TimeContext
import me.altered.platformer.level.data.Ellipse
import me.altered.platformer.level.data.Group
import me.altered.platformer.level.data.Object
import me.altered.platformer.level.data.Polygon
import me.altered.platformer.level.data.Rectangle
import org.jetbrains.skia.Rect
import kotlin.jvm.JvmStatic

sealed class ObjectNode<O : Object>(
    open var obj: O? = null,
    parent: Node? = null,
) : Node2D("", parent) {

    final override var name: String
        get() = obj?.name.toString()
        set(_) = Unit

    var bounds = baseBounds

    /**
     * Evaluates the underlying object and applies the results to the node.
     * Must not be called before [ready], otherwise might yield unexpected results or failures.
     */
    abstract fun TimeContext.eval()

    abstract fun collide(position: Vector2fc, radius: Float, onCollision: (point: Vector2fc) -> Unit)

    protected inline fun rotated(position: Vector2fc, transform: (position: Vector2fc) -> Vector2fc): Vector2fc {
        if (rotation == 0.0f) return transform(position)
        val newPos = position.rotateAround(this.position, rotation)
        return transform(newPos).rotateAround(this.position, -rotation)
    }

    protected inline fun scaled(position: Vector2fc, transform: (position: Vector2fc) -> Vector2fc): Vector2fc {
        if (bounds.width == bounds.height) return transform(position)
        val scale = Vector2f(bounds.height / bounds.width, 1.0f)
        val newPos = position.scaleAround(this.position, scale)
        return transform(newPos).scaleAround(this.position, 1.0f / scale)
    }

    override fun toString(): String = "${super.toString()} $obj"

    companion object {
        @JvmStatic
        protected val baseBounds = Rect(-0.5f, -0.5f, 0.5f, 0.5f)
    }
}

fun ObjectNode<*>.eval(timeContext: TimeContext) {
    timeContext.run { eval() }
}

@Suppress("unchecked_cast")
@Throws(IllegalArgumentException::class)
fun <O : Object> O.toNode(): ObjectNode<O> = when (this) {
    is Ellipse -> EllipseNode(this)
    is Group -> GroupNode(this)
    is Polygon -> TODO("no polygon node yet")
    is Rectangle -> RectangleNode(this)
    else -> throw IllegalArgumentException("Unknown object type")
} as ObjectNode<O>
