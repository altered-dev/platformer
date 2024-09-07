package me.altered.platformer.engine.ui

import me.altered.koml.Vector2f
import me.altered.platformer.engine.node.Node
import org.jetbrains.skia.Rect
import kotlin.math.max
import kotlin.math.min

class Row(
    name: String = "Row",
    parent: Node? = null,
    width: Size = wrap(),
    height: Size = expand(),
    padding: Insets = padding(),
    anchor: Vector2f = Vector2f(0.0f, 0.0f),
    children: Iterable<Node> = emptyList(),
) : UiNode(name, parent, width, height, padding, anchor) {

    init {
        addChildren(children)
    }

    override fun layoutChildren(parentBounds: Rect): Rect {
        // TODO: proper row
        var minLeft = Float.POSITIVE_INFINITY
        var maxRight = Float.NEGATIVE_INFINITY
        var minTop = Float.POSITIVE_INFINITY
        var maxBottom = Float.NEGATIVE_INFINITY

        children.forEach { child ->
            if (child is UiNode) {
                child.layout(parentBounds)
                minLeft = min(minLeft, child.bounds.left)
                maxRight = max(maxRight, child.bounds.right)
                minTop = min(minTop, child.bounds.top)
                maxBottom = max(maxBottom, child.bounds.bottom)
            }
        }

        return Rect.makeLTRB(minLeft, minTop, maxRight, maxBottom)
    }
}
