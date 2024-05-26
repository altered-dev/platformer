package me.altered.platformer.engine.node

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect

open class UiNode(
    name: String,
    parent: Node? = null,
    var width: Size = expand,
    var height: Size = expand,
    var halign: Alignment = start,
    var valign: Alignment = start,
    var margin: Insets = none,
    var padding: Insets = none,
) : Node(name, parent) {

    override fun debugDraw(canvas: Canvas, bounds: Rect) {
        canvas.drawRect(bounds, debugPaint)
    }

    open fun measure(bounds: Rect): Rect {
        val margin = margin
        val padding = (parent as? UiNode)?.padding ?: none
        val w = when (val width = width) {
            is Size.Fixed -> width.value
            Size.Expand -> bounds.width - margin.horizontal - padding.horizontal
            Size.Wrap -> 0.0f // TODO: implement wrapping
        }
        val h = when (val height = height) {
            is Size.Fixed -> height.value
            Size.Expand -> bounds.height - margin.vertical - padding.horizontal
            Size.Wrap -> 0.0f // TODO: implement wrapping
        }

        val x = when (halign) {
            Alignment.START -> margin.left + padding.left
            Alignment.CENTER -> (bounds.width - w) * 0.5f
            Alignment.END -> bounds.width - w - margin.right - padding.right
        }
        val y = when (valign) {
            Alignment.START -> margin.top + padding.top
            Alignment.CENTER -> (bounds.height - h) * 0.5f
            Alignment.END -> bounds.height - w - margin.bottom - padding.bottom
        }

        return Rect.makeXYWH(x, y, w, h)
    }
}
