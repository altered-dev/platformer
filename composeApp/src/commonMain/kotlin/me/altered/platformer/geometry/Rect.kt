package me.altered.platformer.geometry

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Rect

@Stable
fun Rect.scale(sx: Float, sy: Float): Rect {
    return Rect(left * sx, top * sy, right * sx, bottom * sy)
}

@Stable
fun Rect.scale(scale: Float): Rect {
    return Rect(left * scale, top * scale, right * scale, bottom * scale)
}
