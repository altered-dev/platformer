package me.altered.platformer.editor

import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.color
import me.altered.platformer.engine.util.paint
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect
import kotlin.math.roundToInt

class Grid(
    private val bounds: Rect,
) : Node2D("grid") {

    private val originPaint = paint {
        isAntiAlias = true
        mode = PaintMode.STROKE
        color4f = Colors.black
        strokeWidth = 0.0f
    }

    private val gridPaint = paint {
        isAntiAlias = true
        mode = PaintMode.STROKE
        color4f = color(0x40000000)
        strokeWidth = 0.0f
    }

    override fun draw(canvas: Canvas) {
        val offset = -globalPosition
        val scale = globalScale

        val left = offset.x / scale.x
        val right = (this.bounds.width + offset.x) / scale.x
        val top = offset.y / scale.y
        val bottom = (this.bounds.height + offset.y) / scale.y

        canvas
            .translate(0.5f, 0.5f)
            .drawLine(left, 0.0f, right, 0.0f, originPaint)
            .drawLine(0.0f, top, 0.0f, bottom, originPaint)

        val step = 50.0f
        var dx = (left / step).roundToInt() * step
        while (dx < right) {
            canvas.drawLine(dx, top, dx, bottom, gridPaint)
            dx += step
        }
        var dy = (top / step).roundToInt() * step
        while (dy < bottom) {
            canvas.drawLine(left, dy, right, dy, gridPaint)
            dy += step
        }
        canvas.translate(-0.5f, -0.5f)
    }
}
