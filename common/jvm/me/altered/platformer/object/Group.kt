package me.altered.platformer.`object`

import org.jetbrains.skia.Canvas
import me.altered.platformer.node.Node
import me.altered.platformer.node.ParentNode
import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.const

class Group(
    var x: Expression<Float>,
    var y: Expression<Float>,
    var scaleX: Expression<Float> = const(1.0f),
    var scaleY: Expression<Float> = const(1.0f),
    var rotation: Expression<Float> = const(0.0f),
    override val children: Set<Node>,
) : ParentNode() {

    constructor(
        x: Expression<Float>,
        y: Expression<Float>,
        scaleX: Expression<Float>,
        scaleY: Expression<Float>,
        rotation: Expression<Float>,
        vararg children: Node,
    ) : this(x, y, scaleX, scaleY, rotation, setOf(*children))

//    override fun _draw(canvas: Canvas) {
//        // FIXME: i totally need to redo this hack
//        canvas.save()
//        canvas
//            .translate(x.value, y.value)
//            .rotate(rotation.value)
//            .scale(scaleX.value, scaleY.value)
//        // also TODO: calculate bounds based on children
//        children.forEach { it._draw(canvas) }
//        canvas.restore()
//    }
}
