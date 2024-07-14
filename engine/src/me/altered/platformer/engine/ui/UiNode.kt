package me.altered.platformer.engine.ui

import me.altered.koml.Vector2f
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.util.inset
import me.altered.platformer.engine.util.outset
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect
import kotlin.math.max
import kotlin.math.min

open class UiNode(
    name: String = "UiNode",
    parent: Node? = null,
    var width: Size = expand,
    var height: Size = expand,
    var padding: Insets = none,
    val anchor: Vector2f = Vector2f(0.0f, 0.0f),
) : CanvasNode(name, parent) {

    internal var needsLayout = true

    var bounds = Rect(0.0f, 0.0f, 0.0f, 0.0f)
        protected set

    fun invalidateLayout() {
        needsLayout = true
    }

    fun layout(parentBounds: Rect) {
        val width = width
        val height = height
        var left = parentBounds.left
        var right = parentBounds.right
        var top = parentBounds.top
        var bottom = parentBounds.bottom

        when (width) {
            is Size.Expand -> {
                val dw = parentBounds.width - parentBounds.width.coerceIn(width.min, width.max)
                left += dw * anchor.x
                right -= dw * (1.0f - anchor.x)
            }
            is Size.Fixed -> {
                val dw = parentBounds.width - width.value
                left += dw * anchor.x
                right -= dw * (1.0f - anchor.x)
            }
            is Size.Wrap -> Unit
        }

        when (height) {
            is Size.Expand -> {
                val dh = parentBounds.height - parentBounds.height.coerceIn(height.min, height.max)
                top += dh * anchor.y
                bottom -= dh * (1.0f - anchor.y)
            }
            is Size.Fixed -> {
                val dh = parentBounds.height - height.value
                top += dh * anchor.y
                bottom -= dh * (1.0f - anchor.y)
            }
            is Size.Wrap -> Unit
        }

        needsLayout = false
        bounds = Rect.makeLTRB(left, top, right, bottom)
        val childBounds = layoutChildren(bounds.inset(padding)).outset(padding)

        if (width is Size.Wrap) {
            val dw = parentBounds.width - childBounds.width.coerceIn(width.min, width.max)
            left += dw * anchor.x
            right -= dw * (1.0f - anchor.x)
        }
        if (height is Size.Wrap) {
            val dh = parentBounds.height - childBounds.height.coerceIn(height.min, height.max)
            top += dh * anchor.y
            bottom -= dh * (1.0f - anchor.y)
        }
        bounds = Rect.makeLTRB(left, top, right, bottom)
    }

    open fun layoutChildren(parentBounds: Rect): Rect {
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

    override fun debugDraw(canvas: Canvas) {
        canvas.drawRect(bounds, debugPaint)
    }
}
