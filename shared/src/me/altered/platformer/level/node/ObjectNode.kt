package me.altered.platformer.level.node

import me.altered.koml.Vector2fc
import me.altered.koml.rotateAround
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.level.data.Object
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
        protected set

    /**
     * Evaluates the underlying object and applies the results to the node.
     * Must not be called before [ready], otherwise might yield unexpected results or failures.
     */
    abstract fun eval(time: Float)

    abstract fun collide(position: Vector2fc, radius: Float, onCollision: (point: Vector2fc) -> Unit)

    protected inline fun rotated(position: Vector2fc, transform: (position: Vector2fc) -> Vector2fc): Vector2fc {
        if (rotation == 0.0f) return transform(position)
        val newPos = position.rotateAround(this.position, rotation)
        return transform(newPos).rotateAround(this.position, -rotation)
    }

    override fun toString(): String = "${super.toString()} $obj"

    companion object {
        @JvmStatic
        protected val baseBounds = Rect(-0.5f, -0.5f, 0.5f, 0.5f)
    }
}
