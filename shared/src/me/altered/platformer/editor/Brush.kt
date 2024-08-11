package me.altered.platformer.editor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.skia.Shader
import kotlin.jvm.JvmInline

@OptIn(ExperimentalStdlibApi::class)
private val HexFormat = HexFormat {
    upperCase = true
    number.prefix = "#"
}

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
    value class Solid(val color: Int) : Brush {
        @OptIn(ExperimentalStdlibApi::class)
        override fun toString(): String = color.toHexString(HexFormat)
    }

    data class Linear(val x0: Float, val y0: Float, val x1: Float, val y1: Float, val colors: IntArray, val positions: FloatArray?) : Brush {
        override fun toString(): String = "linear(from($x0; $y0) to($x1; $y1), colors=${colors.colorsToString()}, positions=${positions.contentToString()})"

        override fun equals(other: Any?): Boolean {
            // NOTE: auto-generated
            if (this === other) return true
            if (other == null || this::class != other::class) return false
            other as Linear
            if (x0 != other.x0) return false
            if (y0 != other.y0) return false
            if (x1 != other.x1) return false
            if (y1 != other.y1) return false
            if (!colors.contentEquals(other.colors)) return false
            if (positions != null) {
                if (other.positions == null) return false
                if (!positions.contentEquals(other.positions)) return false
            } else if (other.positions != null) return false
            return true
        }

        override fun hashCode(): Int {
            // NOTE: auto-generated
            var result = x0.hashCode()
            result = 31 * result + y0.hashCode()
            result = 31 * result + x1.hashCode()
            result = 31 * result + y1.hashCode()
            result = 31 * result + colors.contentHashCode()
            result = 31 * result + (positions?.contentHashCode() ?: 0)
            return result
        }
    }
}

fun Brush.toShader() = when (this) {
    Brush.Empty -> Shader.makeEmpty()
    is Brush.Solid -> Shader.makeColor(color)
    is Brush.Linear -> Shader.makeLinearGradient(x0, y0, x1, y1, colors, positions)
}

fun emptyBrush(): Brush = Brush.Empty

fun solid(color: Int): Brush = Brush.Solid(color)

fun solid(color: Long): Brush = Brush.Solid(color.toInt())

fun linear(
    x0: Float,
    y0: Float,
    x1: Float,
    y1: Float,
    vararg colors: Long,
): Brush = Brush.Linear(x0, y0, x1, y1, colors.map { it.toInt() }.toIntArray(), null)

@OptIn(ExperimentalStdlibApi::class)
fun IntArray.colorsToString() = joinToString(
    prefix = "[",
    postfix = "]",
) { color -> color.toHexString(HexFormat) }
