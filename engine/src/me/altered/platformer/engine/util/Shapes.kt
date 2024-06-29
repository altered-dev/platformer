package me.altered.platformer.engine.util

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Paint

fun Canvas.drawCrosshair(x: Float, y: Float, size: Float, paint: Paint): Canvas {
    drawLine(x, y - size, x, y + size, paint)
    drawLine(x - size, y, x + size, y, paint)
    return this
}