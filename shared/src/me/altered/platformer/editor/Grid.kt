package me.altered.platformer.editor

import me.altered.koml.Vector2f
import me.altered.platformer.engine.node.Node2D
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.color
import me.altered.platformer.engine.util.paint
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect
import kotlin.math.roundToInt

class Grid : Node2D("grid") {

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

    private val offset = Vector2f()

    override fun draw(canvas: Canvas, bounds: Rect) {
        val parent = parent as? Node2D ?: return
        parent.position.negate(offset)
        val scale = parent.scale

        canvas
            .translate(0.5f, 0.5f)

        val left = offset.x / scale.x
        val right = (bounds.width + offset.x) / scale.x
        val top = offset.y / scale.y
        val bottom = (bounds.height + offset.y) / scale.y

        canvas
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
