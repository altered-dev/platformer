package me.altered.platformer.editor

import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.graphics.Paint
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.graphics.drawLine
import me.altered.platformer.engine.node.aspectRatio
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect
import kotlin.math.roundToInt

class Grid(
    private val bounds: Rect,
) : Node2D("grid") {

    var step = 1.0f

    private val originPaint = Paint {
        isAntiAlias = true
        mode = PaintMode.STROKE
        color = Color.Black
        strokeWidth = 0.0f
    }

    private val gridPaint = Paint {
        isAntiAlias = true
        mode = PaintMode.STROKE
        color = Color(0x40000000)
        strokeWidth = 0.0f
    }

    override fun draw(canvas: Canvas) {
        val height = viewport?.size ?: return
        val width = height * (window?.aspectRatio ?: return)

        canvas
            .drawLine(0.0f, height * -0.5f, 0.0f, height * 0.5f, originPaint)
            .drawLine(width * -0.5f, 0.0f, width * 0.5f, 0.0f, originPaint)

        val offset = -globalPosition

        val left = width * -0.5f + offset.x
        val right = width * 0.5f + offset.x
        val top = height * -0.5f + offset.y
        val bottom = height * 0.5f + offset.y

        val step = step
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

    override fun debugDraw(canvas: Canvas) = Unit
}
