package me.altered.platformer.scene.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.altered.platformer.node.Node2D
import kotlin.math.roundToInt

class Grid(
    var step: Float = 1.0f,
) : Node2D("grid") {

    override fun DrawScope.draw() {
        val (left, top) = -size.center
        val (right, bottom) = size.center

        drawLine(Color.Black, Offset(left, 0.0f), Offset(right, 0.0f))
        drawLine(Color.Black, Offset(0.0f, top), Offset(0.0f, bottom))

        val step = step
        var dx = (left / step).roundToInt() * step
        while (dx < right) {
            drawLine(gridColor, Offset(dx, top), Offset(dx, bottom))
            dx += step
        }
        var dy = (top / step).roundToInt() * step
        while (dy < bottom) {
            drawLine(gridColor, Offset(left, dy), Offset(right, dy))
            dy += step
        }
    }

    companion object {

        private val gridColor = Color(0x40000000)
    }
}