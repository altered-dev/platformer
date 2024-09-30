package me.altered.platformer.geometry

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import me.altered.platformer.level.data.Brush

import androidx.compose.ui.graphics.Brush as ComposeBrush

@Stable
fun Brush.toComposeBrush(): ComposeBrush {
    return when (this) {
        Brush.Empty -> SolidColor(Color.Transparent)
        is Brush.Blend -> TODO()
        is Brush.Linear -> if (positions != null) {
            ComposeBrush.linearGradient(
                colorStops = positions!!.zip(colors.map { Color(it.value) }).toTypedArray(),
                start = Offset(x0, y0),
                end = Offset(x1, y1),
            )
        } else {
            ComposeBrush.linearGradient(
                colors = colors.map { Color(it.value) },
                start = Offset(x0, y0),
                end = Offset(x1, y1),
            )
        }
        is Brush.Radial -> if (positions != null) {
            ComposeBrush.radialGradient(
                colorStops = positions!!.zip(colors.map { Color(it.value) }).toTypedArray(),
                center = Offset(x, y),
                radius = r,
            )
        } else {
            ComposeBrush.radialGradient(
                colors = colors.map { Color(it.value) },
                center = Offset(x, y),
                radius = r,
            )
        }
        is Brush.Solid -> SolidColor(Color(color.value))
        is Brush.Sweep -> if (positions != null) {
            ComposeBrush.sweepGradient(
                colorStops = positions!!.zip(colors.map { Color(it.value) }).toTypedArray(),
                center = Offset(x, y),
            )
        } else {
            ComposeBrush.sweepGradient(
                colors = colors.map { Color(it.value) },
                center = Offset(x, y),
            )
        }
        is Brush.TwoPointConical -> TODO()
    }
}
