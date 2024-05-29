package me.altered.platformer.`object`

import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.Node2D
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
}
