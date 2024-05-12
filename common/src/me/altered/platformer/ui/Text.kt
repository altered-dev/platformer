package me.altered.platformer.ui

import org.jetbrains.skia.Canvas
import me.altered.platformer.engine.io.font
import me.altered.platformer.engine.io.resource
import me.altered.platformer.engine.node.Node
import me.altered.platformer.skija.buildPaint

class Text(
    override var name: String,
    var x: Float,
    var y: Float,
    var color: Int,
) : Node(name) {

    private val paint = buildPaint {
        color = this@Text.color
    }

    override fun draw(canvas: Canvas, width: Float, height: Float): Unit = canvas.run {
        drawString(name, x, y, font, paint)
    }

    companion object {

        private val font = font(resource("fonts/Inter-Regular.ttf"), 13.0f)
    }
}
