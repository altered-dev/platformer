package me.altered.platformer.engine.ui

import me.altered.platformer.engine.graphics.drawRRect
import me.altered.platformer.engine.graphics.spread
import me.altered.platformer.engine.node.Node
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.RRect
import org.jetbrains.skia.Rect
import org.jetbrains.skia.Shader
import kotlin.math.max

open class Row(
    name: String = "Row",
    parent: Node? = null,
    width: Size = expand(),
    height: Size = expand(),
    var cornerRadius: Float = 0.0f,
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

        var totalWidth = 0.0f
        var maxHeight = 0.0f

        val measured = uiChildren.map { child ->
            val (w, h) = child.measure(childWidth, childHeight)
            totalWidth += w
            maxHeight = max(maxHeight, h)
            w to h
        }.toList()
        totalWidth += spacing * (measured.size - 1).coerceAtLeast(0)

        wrapSelf(this.width, totalWidth, padding.horizontal)?.let { measuredWidth = it }
        wrapSelf(this.height, maxHeight, padding.vertical)?.let { measuredHeight = it }

        var x = when (horizontalAlignment) {
            Alignment.Start -> padding.left
            Alignment.Center -> (measuredWidth + padding.left - padding.right - totalWidth) * 0.5f
            Alignment.End -> measuredWidth - padding.right - totalWidth
        }
        uiChildren.forEachIndexed { index, child ->
            val (w, h) = measured[index]
            val y = when (verticalAlignment) {
                Alignment.Start -> padding.top
                Alignment.Center -> (measuredHeight + padding.top - padding.bottom - h) * 0.5f
                Alignment.End -> measuredHeight - padding.bottom - h
            }
            child.bounds = Rect.makeXYWH(x, y, w, h)
            child.globalBounds = child.bounds.offset(globalBounds.left, globalBounds.top)
            x += w + spacing
        }

        isMeasured = true
        return measuredWidth to measuredHeight
    }

    override fun draw(canvas: Canvas) {
        val rect = RRect.makeXYWH(0.0f, 0.0f, bounds.width, bounds.height, cornerRadius)
        canvas
            .drawRRect(rect, fillPaint)
            .drawRRect(rect.spread(-0.5f), strokePaint)
        // temporary
        isMeasured = false
    }
}