package me.altered.platformer.ui

import io.github.humbleui.skija.Canvas
import io.github.humbleui.skija.Color4f
import me.altered.platformer.io.font
import me.altered.platformer.node.Node
import me.altered.platformer.skija.buildPaint

class Text(
    override var name: String,
    var x: Float,
    var y: Float,
    var color: Color4f,
) : Node() {

    private val paint = buildPaint {
        color4f = this@Text.color
    }

    override fun draw(canvas: Canvas): Unit = canvas.run {
        drawString(name, x, y, font, paint)
    }

    companion object {

        private val font = font("fonts/Inter-Regular.ttf", 13.0f)
    }
}
