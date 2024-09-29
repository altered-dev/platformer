package me.altered.platformer.editor

import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.graphics.Paint
import me.altered.platformer.engine.graphics.component1
import me.altered.platformer.engine.graphics.component2
import me.altered.platformer.engine.graphics.component3
import me.altered.platformer.engine.graphics.component4
import me.altered.platformer.engine.graphics.drawLine
import me.altered.platformer.engine.node2d.Node2D
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode
import kotlin.math.roundToInt

class Grid(
    var step: Float = 1.0f,
) : Node2D("grid") {

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
        val (left, top, right, bottom) = viewport?.windowBounds ?: return

        canvas
            .drawLine(left, 0.0f, right, 0.0f, originPaint)
            .drawLine(0.0f, top, 0.0f, bottom, originPaint)

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
    }

    override fun debugDraw(canvas: Canvas) = Unit
}
