package me.altered.platformer.objects

import me.altered.platformer.engine.util.add
import me.altered.platformer.timeline.Expression
import org.jetbrains.skia.Rect

class Group(
    name: String,
    override var xExpr: Expression<Float>,
    override var yExpr: Expression<Float>,
    override var rotationExpr: Expression<Float>,
) : ObjectNode(name), ObjectContainer {

    override val bounds: Rect
        get() {
            var b = Rect.makeWH(0.0f, 0.0f)
            children.forEach { child ->
                if (child is ObjectNode) {
                    b.add(child.bounds)
                }
            }
            return b
        }

    override fun eval(time: Float) {
        super.eval(time)
        children.forEach { child ->
            if (child is ObjectNode) {
                child.eval(time)
            }
        }
    }

    override fun place(obj: ObjectNode) {
        addChild(obj)
    }

    override fun remove(obj: ObjectNode) {
        removeChild(obj)
    }

    override fun toString(): String = """
        ${super.toString()} (
            x = $xExpr
            y = $yExpr
            rotation = $rotationExpr
        )
    """.trimIndent()
}
