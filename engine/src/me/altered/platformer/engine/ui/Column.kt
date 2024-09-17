package me.altered.platformer.engine.ui

import me.altered.platformer.engine.graphics.drawRect
import me.altered.platformer.engine.node.Node
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect
import org.jetbrains.skia.Shader
import kotlin.math.max

open class Column(
    name: String = "Row",
    parent: Node? = null,
    width: Size = expand(),
    height: Size = expand(),
    var padding: Insets = padding(),
    var horizontalAlignment: Alignment = start,
    var verticalAlignment: Alignment = start,
    var spacing: Float = 0.0f,
    fill: Shader = Shader.makeEmpty(),
    stroke: Shader = Shader.makeEmpty(),
    strokeWidth: Float = 0.0f,
) : UiNode(name, parent, width, height, fill, stroke, strokeWidth) {

    override fun measure(width: Float, height: Float): Pair<Float, Float> {
        var (measuredWidth, childWidth) = measureSelf(this.width, width, padding.horizontal)
        var (measuredHeight, childHeight) = measureSelf(this.height, height, padding.vertical)

        var maxWidth = 0.0f
        var totalHeight = 0.0f

        val measured = uiChildren.map { child ->
            val (w, h) = child.measure(childWidth, childHeight)
            maxWidth = max(maxWidth, w)
            totalHeight += h
            w to h
        }.toList()
        totalHeight += spacing * (measured.size - 1).coerceAtLeast(0)

        wrapSelf(this.width, maxWidth, padding.horizontal)?.let { measuredWidth = it }
        wrapSelf(this.height, totalHeight, padding.vertical)?.let { measuredHeight = it }

        var y = when (verticalAlignment) {
            Alignment.Start -> padding.top
            Alignment.Center -> (measuredHeight + padding.top - padding.bottom - totalHeight) * 0.5f
            Alignment.End -> measuredHeight - padding.bottom - totalHeight
        }
        uiChildren.forEachIndexed { index, child ->
            val (w, h) = measured[index]
            val x = when (horizontalAlignment) {
                Alignment.Start -> padding.left
                Alignment.Center -> (measuredWidth + padding.left - padding.right - w) * 0.5f
                Alignment.End -> measuredWidth - padding.right - w
            }
            child.bounds = Rect.makeXYWH(x, y, w, h)
            child.globalBounds = child.bounds.offset(globalBounds.left, globalBounds.top)
            y += h + spacing
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