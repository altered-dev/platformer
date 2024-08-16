package me.altered.platformer.engine.graphics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.skia.BlendMode
import org.jetbrains.skia.GradientStyle
import org.jetbrains.skia.Shader
import kotlin.jvm.JvmInline

@Serializable
sealed interface Brush {

    @Serializable
    @SerialName("empty")
    data object Empty : Brush {
        override fun toString(): String = "empty"
    }

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
    @SerialName("twoPointConical")
    data class TwoPointConical(
        val x0: Float,
        val y0: Float,
        val r0: Float,
        val x1: Float,
        val y1: Float,
        val r1: Float,
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

    @Serializable
    @SerialName("blend")
    data class Blend(
        val src: Brush,
        val dst: Brush,
        // TODO: to own enum
        val mode: BlendMode,
    ) : Brush
}

/**
 * TODO: optimize, perhaps cache shaders
 */
fun Brush.toShader(): Shader = when (this) {
    Brush.Empty -> Shader.makeEmpty()
    is Brush.Solid -> Shader.makeColor(color.value)
    is Brush.Linear -> Shader.makeLinearGradient(
        x0 = x0,
        y0 = y0,
        x1 = x1,
        y1 = y1,
        colors = IntArray(colors.size) { colors[it].value },
        positions = positions?.toFloatArray(),
    )
    is Brush.Radial -> Shader.makeRadialGradient(
        x = x,
        y = y,
        r = r,
        colors = IntArray(colors.size) { colors[it].value },
        positions = positions?.toFloatArray(),
    )
    is Brush.TwoPointConical -> Shader.makeTwoPointConicalGradient(
        x0 = x0,
        y0 = y0,
        r0 = r0,
        x1 = x1,
        y1 = y1,
        r1 = r1,
        colors = IntArray(colors.size) { colors[it].value },
        positions = positions?.toFloatArray(),
    )
    is Brush.Sweep -> Shader.makeSweepGradient(
        x = x,
        y = y,
        startAngle = startAngle,
        endAngle = endAngle,
        colors = IntArray(colors.size) { colors[it].value },
        positions = positions?.toFloatArray(),
        style = GradientStyle.DEFAULT,
    )
    is Brush.Blend -> Shader.makeBlend(
        mode = mode,
        dst = dst.toShader(),
        src = src.toShader(),
    )
}

fun emptyBrush(): Brush = Brush.Empty

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

fun twoPointConical(
    x0: Float,
    y0: Float,
    r0: Float,
    x1: Float,
    y1: Float,
    r1: Float,
    colors: List<Color>,
    positions: List<Float>? = null,
): Brush = Brush.TwoPointConical(x0, y0, r0, x1, y1, r1, colors, positions)

fun sweep(
    x: Float,
    y: Float,
    startAngle: Float,
    endAngle: Float,
    colors: List<Color>,
    positions: List<Float>? = null,
): Brush = Brush.Sweep(x, y, startAngle, endAngle, colors, positions)

fun blend(
    src: Brush,
    dst: Brush,
    mode: BlendMode,
): Brush = Brush.Blend(src, dst, mode)
