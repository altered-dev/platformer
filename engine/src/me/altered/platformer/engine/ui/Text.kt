package me.altered.platformer.engine.ui

import me.altered.koml.Vector2f
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.paint
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Font
import org.jetbrains.skia.Rect
import org.jetbrains.skia.TextBlob
import org.jetbrains.skia.shaper.Shaper

class Text(
    text: String = "Text",
    parent: Node? = null,
    width: Size = wrap,
    height: Size = wrap,
    padding: Insets = none,
    anchor: Vector2f = Vector2f(0.5f, 0.5f),
) : UiNode(text, parent, width, height, padding, anchor) {

    private lateinit var textBlob: TextBlob

    var text: String
        get() = name
        set(value) {
            val newBlob = when (val width = width) {
                is Size.Expand -> shaper.shape(value, font, bounds.width)
                is Size.Fixed -> shaper.shape(value, font, width.value)
                is Size.Wrap -> shaper.shape(value, font)
            }
            newBlob?.let { textBlob = it }
            name = value
        }

    init {
        this.text = text
    }

    override fun draw(canvas: Canvas) {
        canvas.drawTextBlob(textBlob, bounds.left, bounds.top, paint)
    }

    override fun layoutChildren(parentBounds: Rect): Rect {
        return textBlob.bounds
    }

    companion object {
        val shaper = Shaper.make()

        val font = Font(null, 13.0f)

        val paint = paint {
            color4f = Colors.black
        }
    }
}