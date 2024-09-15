package me.altered.platformer.engine.ui

import me.altered.platformer.engine.graphics.emptyRect
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.Node
import org.jetbrains.skia.Rect

abstract class UiNode(
    name: String = "UiNode",
    parent: Node? = null,
) : CanvasNode(name, parent) {

    protected val uiChildren = children.asSequence().filterIsInstance<UiNode>()

    var bounds: Rect = emptyRect()
    var isMeasured = false

    abstract fun measure(width: Float, height: Float): Pair<Float, Float>

    protected fun measureSelf(size: Size, constraint: Float, padding: Float = 0.0f): Pair<Float, Float> {
        return when (size) {
            is Size.Fixed -> size.value to size.value - padding
            is Size.Expand -> {
                val measured = (constraint * size.fraction).coerceIn(size.min, size.max)
                measured to measured - padding
            }
            is Size.Wrap -> padding to constraint - padding
        }
    }

    protected fun wrapSelf(size: Size, max: Float, padding: Float = 0.0f): Float? {
        if (size !is Size.Wrap) return null
        return (max + padding).coerceIn(size.min, size.max)
    }
}
