package me.altered.platformer.engine.ui

import me.altered.platformer.engine.graphics.drawRect
import me.altered.platformer.engine.node.Node
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect
import org.jetbrains.skia.Shader
import kotlin.math.max

open class Box(
    name: String = "Box",
    parent: Node? = null,
    width: Size = expand(),
    height: Size = expand(),
    var padding: Insets = padding(),
    var horizontalAlignment: Alignment = start,
    var verticalAlignment: Alignment = start,
    fill: Shader = Shader.makeEmpty(),
    stroke: Shader = Shader.makeEmpty(),
    strokeWidth: Float = 0.0f,
) : UiNode(name, parent, width, height, fill, stroke, strokeWidth) {

    override fun measure(width: Float, height: Float): Pair<Float, Float> {
        var (measuredWidth, childWidth) = measureSelf(this.width, width, padding.horizontal)
        var (measuredHeight, childHeight) = measureSelf(this.height, height, padding.vertical)

        var maxWidth = 0.0f
        var maxHeight = 0.0f

        val measured = children.map { child ->
            if (child !is UiNode) return@map null
            val (w, h) = child.measure(childWidth, childHeight)
            maxWidth = max(maxWidth, w)
            maxHeight = max(maxHeight, h)
            w to h
        }

        wrapSelf(this.width, maxWidth, padding.horizontal)?.let { measuredWidth = it }
        wrapSelf(this.height, maxHeight, padding.vertical)?.let { measuredHeight = it }

        children.forEachIndexed { index, child ->
            if (child !is UiNode) return@forEachIndexed
            val (w, h) = measured[index] ?: return@forEachIndexed
            val x = when (horizontalAlignment) {
                Alignment.Start -> padding.left
                Alignment.Center -> (measuredWidth + padding.left - padding.right - w) * 0.5f
                Alignment.End -> measuredWidth - padding.right - w
            }
            val y = when (verticalAlignment) {
                Alignment.Start -> padding.top
                Alignment.Center -> (measuredHeight + padding.top - padding.bottom - h) * 0.5f
                Alignment.End -> measuredHeight - padding.bottom - h
            }
            child.bounds = Rect.makeXYWH(x, y, w, h)
            child.globalBounds = child.bounds.offset(globalBounds.left, globalBounds.top)
        }

        isMeasured = true
        return measuredWidth to measuredHeight
    }

    override fun draw(canvas: Canvas) {
        val rect = Rect.makeWH(bounds.width, bounds.height)
        canvas
            .drawRect(rect, fillPaint)
            .drawRect(rect, strokePaint)
        // temporary
        isMeasured = false
    }
}
