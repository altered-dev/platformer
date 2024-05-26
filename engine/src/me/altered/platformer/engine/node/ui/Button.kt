package me.altered.platformer.engine.node.ui

import me.altered.platformer.engine.node.Alignment
import me.altered.platformer.engine.node.Insets
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.Size
import me.altered.platformer.engine.node.UiNode
import me.altered.platformer.engine.node.center
import me.altered.platformer.engine.node.expand
import me.altered.platformer.engine.node.none
import me.altered.platformer.engine.node.start
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.paint
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect

class Button(
    text: String,
    parent: Node? = null,
    width: Size = expand,
    height: Size = expand,
    halign: Alignment = start,
    valign: Alignment = start,
    margin: Insets = none,
    padding: Insets = none,
) : UiNode("Button($text)", parent, width, height, halign, valign, margin, padding) {

    private val textNode = +Text(text, halign = center, valign = center)

    var text: String by textNode::text

    override val name: String
        get() = "Button($text)"

    override fun draw(canvas: Canvas, bounds: Rect) {
        canvas.drawRect(bounds, paint)
    }

    companion object {

        private val paint = paint { color4f = Colors.green }
    }
}
