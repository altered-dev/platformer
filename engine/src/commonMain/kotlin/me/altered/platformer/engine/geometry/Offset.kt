package me.altered.platformer.engine.geometry

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import me.altered.platformer.engine.math.deg2rad
import kotlin.math.cos
import kotlin.math.sin

@Stable
operator fun Offset.times(size: Size): Offset {
    return Offset(
        x = x * size.width,
        y = y * size.height,
    )
}

@Stable
fun Offset.normalize(length: Float): Offset {
    val invLength = length / getDistance()
    return Offset(
        x = x * invLength,
        y = y * invLength,
    )
}

@Stable
fun Offset.rotateAround(point: Offset, deg: Float): Offset {
    if (deg == 0.0f) return this
    val sin = sin(deg.deg2rad())
    val cos = cos(deg.deg2rad())
    val dx = ((x - point.x) * cos) - ((point.y - y) * sin) + point.x
    val dy =  point.y - ((point.y - y) * cos) - ((x - point.x) * sin)
    return Offset(dx, dy)
}

@Stable
fun Offset.scaleAround(point: Offset, scale: Size): Offset {
    return (this - point) * scale + point
}
