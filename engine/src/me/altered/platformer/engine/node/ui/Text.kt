package me.altered.platformer.engine.node.ui

import me.altered.platformer.engine.node.Alignment
import me.altered.platformer.engine.node.Insets
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.Size
import me.altered.platformer.engine.node.UiNode
import me.altered.platformer.engine.node.none
import me.altered.platformer.engine.node.start
import me.altered.platformer.engine.node.wrap
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.paint
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Font
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.Rect
import org.jetbrains.skia.TextBlobBuilder
import org.jetbrains.skia.TextLine
import org.jetbrains.skia.Typeface

class Text(
    var text: String,
    parent: Node? = null,
    width: Size = wrap,
    height: Size = wrap,
    halign: Alignment = start,
    valign: Alignment = start,
    margin: Insets = none,
    padding: Insets = none,
) : UiNode(text, parent, width, height, halign, valign, margin, padding) {

    override val name: String
        get() = text

    override fun draw(canvas: Canvas, bounds: Rect) {
        canvas.drawString(text, 0.0f, bounds.height, font, paint)
    }

    override fun measure(bounds: Rect): Rect {
        val margin = margin
        val padding = (parent as? UiNode)?.padding ?: none
        val textSize = font.measureText(text, paint)

        val w = when (val width = width) {
            is Size.Fixed -> width.value
            Size.Expand -> bounds.width - margin.horizontal - padding.horizontal
            Size.Wrap -> textSize.width
        }
        val h = when (val height = height) {
            is Size.Fixed -> height.value
            Size.Expand -> bounds.height - margin.vertical - padding.horizontal
            Size.Wrap -> textSize.height
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

    companion object {

        // TODO: customize style
        private val font = Font(Typeface.makeDefault(), 13.0f)

        private val paint = paint { color4f = Colors.black }
    }
}
