package me.altered.platformer.level.data

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.engine.graphics.Color
import kotlin.jvm.JvmInline
import androidx.compose.ui.graphics.Brush as ComposeBrush
import androidx.compose.ui.graphics.Color as ComposeColor

@Serializable
@SerialName("brush")
sealed interface Brush {

    @JvmInline
    @Serializable
    @SerialName("solid")
    value class Solid(val color: Color) : Brush {
        override fun toString(): String = color.toString()
    }

    @Serializable
    @SerialName("linear")
    data class Linear(
        val x0: Float,
        val y0: Float,
        val x1: Float,
        val y1: Float,
        val colors: List<Color>,
        val positions: List<Float>?,
    ) : Brush

    @Serializable
    @SerialName("radial")
    data class Radial(
        val x: Float,
        val y: Float,
        val r: Float,
        val colors: List<Color>,
        val positions: List<Float>?,
    ) : Brush

    @Serializable
    @SerialName("sweep")
    data class Sweep(
        val x: Float,
        val y: Float,
        val startAngle: Float,
        val endAngle: Float,
        val colors: List<Color>,
        val positions: List<Float>?,
    ) : Brush
}

fun solid(color: Color): Brush = Brush.Solid(color)

fun solid(color: Int): Brush = Brush.Solid(Color(color))

fun solid(color: Long): Brush = Brush.Solid(Color(color))

fun linear(
    x0: Float,
    y0: Float,
    x1: Float,
    y1: Float,
    colors: List<Color>,
    positions: List<Float>? = null,
): Brush = Brush.Linear(x0, y0, x1, y1, colors, positions)

fun radial(
    x: Float,
    y: Float,
    r: Float,
    colors: List<Color>,
    positions: List<Float>? = null,
): Brush = Brush.Radial(x, y, r, colors, positions)

fun sweep(
    x: Float,
    y: Float,
    startAngle: Float,
    endAngle: Float,
    colors: List<Color>,
    positions: List<Float>? = null,
): Brush = Brush.Sweep(x, y, startAngle, endAngle, colors, positions)

@Stable
fun Brush.toComposeBrush() = when (this) {
    is Brush.Solid -> SolidColor(ComposeColor(color.value))
    is Brush.Linear -> if (positions != null) {
        ComposeBrush.linearGradient(
            colorStops = positions.zip(colors.map { ComposeColor(it.value) }).toTypedArray(),
            start = Offset(x0, y0),
            end = Offset(x1, y1),
        )
    } else {
        ComposeBrush.linearGradient(
            colors = colors.map { ComposeColor(it.value) },
            start = Offset(x0, y0),
            end = Offset(x1, y1),
        )
    }
    is Brush.Radial -> if (positions != null) {
        ComposeBrush.radialGradient(
            colorStops = positions.zip(colors.map { ComposeColor(it.value) }).toTypedArray(),
            center = Offset(x, y),
            radius = r,
        )
    } else {
        ComposeBrush.radialGradient(
            colors = colors.map { ComposeColor(it.value) },
            center = Offset(x, y),
            radius = r,
        )
    }
    is Brush.Sweep -> if (positions != null) {
        ComposeBrush.sweepGradient(
            colorStops = positions.zip(colors.map { ComposeColor(it.value) }).toTypedArray(),
            center = Offset(x, y),
        )
    } else {
        ComposeBrush.sweepGradient(
            colors = colors.map { ComposeColor(it.value) },
            center = Offset(x, y),
        )
    }
}
