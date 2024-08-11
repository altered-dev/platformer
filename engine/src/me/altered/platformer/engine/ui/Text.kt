package me.altered.platformer.engine.ui

import me.altered.koml.Vector2f
import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.graphics.Paint
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.graphics.drawTextBlob
import me.altered.platformer.engine.graphics.emptyRect
import me.altered.platformer.engine.util.observable
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
    anchor: Vector2f = Vector2f(0.0f, 0.0f),
    color: Color = Color.Black,
) : UiNode(text, parent, width, height, padding, anchor) {

    private var textBlob: TextBlob? = null

    private val paint = Paint {
        this.color = color
    }

    var text: String
        get() = name
        set(value) {
            if (value == name) return
            textBlob = makeBlob(value)
            name = value
            invalidateLayout()
        }

    var color by observable(color) {
        paint.color = it
    }

    init {
        textBlob = makeBlob(text)
    }

    override fun draw(canvas: Canvas) {
        textBlob?.let { canvas.drawTextBlob(it, bounds.left + padding.left, bounds.top + padding.top, paint) }
    }

    override fun layoutChildren(parentBounds: Rect): Rect {
        return textBlob?.bounds ?: emptyRect()
    }

    override fun debugDraw(canvas: Canvas) = Unit

    private fun makeBlob(value: String): TextBlob? {
        return when (val width = width) {
            is Size.Expand -> shaper.shape(value, font, bounds.width)
            is Size.Fixed -> shaper.shape(value, font, width.value)
            is Size.Wrap -> shaper.shape(value, font)
        }
    }

    companion object {
        val shaper = Shaper.makeShaperDrivenWrapper()

        val font = Font(null, 13.0f)
    }
}
