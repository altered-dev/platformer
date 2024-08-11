package me.altered.platformer.engine.graphics

import org.jetbrains.skia.Canvas

fun Canvas.drawCrosshair(x: Float, y: Float, size: Float, paint: Paint): Canvas {
    drawLine(x, y - size, x, y + size, paint)
    drawLine(x - size, y, x + size, y, paint)
    return this
}
