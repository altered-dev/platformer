package me.altered.platformer.objects

import me.altered.platformer.engine.util.add
import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.const
import org.jetbrains.skia.Rect

class Group(
    name: String,
    override var xExpr: Expression<Float>,
    override var yExpr: Expression<Float>,
    override var rotationExpr: Expression<Float> = const(0.0f),
) : ObjectNode(name) {

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
}
