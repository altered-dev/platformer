package me.altered.platformer.objects

import me.altered.koml.Vector2fc
import me.altered.koml.rotateAround
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.player.Player
import me.altered.platformer.timeline.Expression
import org.jetbrains.skia.Rect

abstract class ObjectNode(
    name: String,
    parent: Node? = null,
) : Node2D(name, parent) {

    abstract var xExpr: Expression<Float>
    abstract var yExpr: Expression<Float>
    abstract var rotationExpr: Expression<Float>

    abstract val bounds: Rect

    open fun eval(time: Float) {
        position.set(
            x = xExpr.eval(time),
            y = yExpr.eval(time),
        )
        rotation = rotationExpr.eval(time)
    }

    open fun collide(position: Vector2fc, radius: Float): Vector2fc? {
        return null
    }

    protected inline fun rotated(position: Vector2fc, transform: (position: Vector2fc) -> Vector2fc): Vector2fc {
        if (rotation == 0.0f) return transform(position)
        val newPos = position.rotateAround(this.position, rotation)
        return transform(newPos).rotateAround(this.position, -rotation)
    }
}
