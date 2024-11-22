package me.altered.platformer.scene.editor.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.altered.platformer.engine.node.Node2D
import kotlin.math.roundToInt

class Grid(
    var step: Float = 1.0f,
    val screenToWorld: Offset.(Size) -> Offset = { this },
) : Node2D("grid") {

    override fun DrawScope.draw() {
        val bounds = size.toRect()
        val (left, top) = bounds.topLeft.screenToWorld(size)
        val (right, bottom) = bounds.bottomRight.screenToWorld(size)

        drawLine(Color.Companion.Black, Offset(left, 0.0f), Offset(right, 0.0f))
        drawLine(Color.Companion.Black, Offset(0.0f, top), Offset(0.0f, bottom))

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
