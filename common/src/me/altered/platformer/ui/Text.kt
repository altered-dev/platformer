package me.altered.platformer.ui

import org.jetbrains.skia.Canvas
import me.altered.platformer.engine.io.font
import me.altered.platformer.engine.io.resource
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.util.paint
import org.jetbrains.skia.Color4f
import org.jetbrains.skia.Rect

class Text(
    var text: () -> String,
    var x: Float,
    var y: Float,
    var color: Color4f,
) : Node(text()) {

    override val name: String
        get() = text()

    private val paint = paint {
        color4f = this@Text.color
    }

    constructor(text: String, x: Float, y: Float, color: Color4f) : this({ text }, x, y, color)

    override fun draw(canvas: Canvas, bounds: Rect): Unit = canvas.run {
        drawString(name, x, y, font, paint)
    }

    companion object {

        private val font = font(resource("fonts/Inter-Regular.ttf"), 13.0f)
    }
}
