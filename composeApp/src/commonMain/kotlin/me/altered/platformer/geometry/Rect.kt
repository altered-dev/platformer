package me.altered.platformer.geometry

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Rect

@Stable
fun Rect.scale(sx: Float, sy: Float): Rect {
    return Rect(left * sx, top * sy, right * sx, bottom * sy)
}
