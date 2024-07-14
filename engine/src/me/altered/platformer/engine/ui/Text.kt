package me.altered.platformer.engine.ui

import me.altered.koml.Vector2f
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.Paint
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
            if (value == name) return
            makeBlob(value)?.let { textBlob = it }
            name = value
            needsLayout = true
        }

    init {
        makeBlob(name)?.let { textBlob = it }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawTextBlob(textBlob, bounds.left, bounds.top, paint)
    }

    override fun layoutChildren(parentBounds: Rect): Rect {
        return textBlob.bounds
    }

    private fun makeBlob(value: String): TextBlob? {
        return when (val width = width) {
            is Size.Expand -> shaper.shape(value, font, bounds.width)
            is Size.Fixed -> shaper.shape(value, font, width.value)
            is Size.Wrap -> shaper.shape(value, font)
        }
    }

    companion object {
        val shaper = Shaper.make()

        val font = Font(null, 13.0f)

        val paint = Paint {
            color4f = Colors.Black
        }
    }
}