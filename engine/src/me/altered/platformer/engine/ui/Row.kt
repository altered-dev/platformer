package me.altered.platformer.engine.ui

import me.altered.platformer.engine.graphics.Paint
import me.altered.platformer.engine.graphics.drawRect
import me.altered.platformer.engine.node.Node
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect
import org.jetbrains.skia.Shader
import kotlin.math.max

class Row(
    name: String = "Row",
    parent: Node? = null,
    var width: Size = expand(),
    var height: Size = expand(),
    var padding: Insets = padding(),
    var horizontalAlignment: Alignment = start,
    var verticalAlignment: Alignment = start,
    var spacing: Float = 0.0f,
    fill: Shader = Shader.makeEmpty(),
    stroke: Shader = Shader.makeEmpty(),
    strokeWidth: Float = 0.0f,
) : UiNode(name, parent) {

    private val fillPaint = Paint {
        mode = PaintMode.FILL
        shader = fill
    }
    private val strokePaint = Paint {
        mode = PaintMode.STROKE
        shader = stroke
        this.strokeWidth = strokeWidth
    }

    var fill by fillPaint::shader
    var stroke by strokePaint::shader
    var strokeWidth by strokePaint::strokeWidth

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
            x += w + spacing
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