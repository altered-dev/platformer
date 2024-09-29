package me.altered.platformer.engine.graphics

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * A class representing a color in ARGB color space.
 */
@JvmInline
@Serializable
@OptIn(ExperimentalStdlibApi::class)
value class Color(val value: Int) {

    /**
     * The alpha component of the color.
     */
    val a: Int get() = value shr 24 and 0xFF

    /**
     * The red component of the color.
     */
    val r: Int get() = value shr 16 and 0xFF

    /**
     * The green component of the color.
     */
    val g: Int get() = value shr 8 and 0xFF

    /**
     * The blue component of the color.
     */
    val b: Int get() = value and 0xFF

    constructor(value: Long) : this(value.toInt())

    /**
     * Creates a color value out of a hex formatted string.
     *
     * @param hex a string formatted as `#AARRGGBB`.
     */
    constructor(hex: String) : this(hex.hexToInt(hexFormat))

    /**
     * Creates a color value out of the [r], [g], [b] and [a] components.
     *
     * Only the 8 least significant bits of the numbers are used.
     */
    constructor(r: Int, g: Int, b: Int, a: Int = 255) : this(
        a and 0xFF shl 24
                or (r and 0xFF shl 16)
                or (g and 0xFF shl 8)
                or (b and 0xFF)
    )

    fun copy() = Color(value)

    fun copy(
        r: Int = this.r,
        g: Int = this.g,
        b: Int = this.b,
        a: Int = this.a,
    ) = Color(r, g, b, a)

    fun lerp(to: Color, t: Float): Color {
        val (r1, g1, b1, a1) = this
        val (r2, g2, b2, a2) = to
        val r = (r1 + t * (r2 - r1)).toInt()
        val g = (g1 + t * (g2 - g1)).toInt()
        val b = (b1 + t * (b2 - b1)).toInt()
        val a = (a1 + t * (a2 - a1)).toInt()
        return Color(r, g, b, a)
    }

    operator fun component1() = r

    operator fun component2() = g

    operator fun component3() = b

    operator fun component4() = a

    override fun toString() = value.toHexString(hexFormat)

    companion object {

        val White =       Color(0xFF_FF_FF_FF)
        val Black =       Color(0xFF_00_00_00)
        val Transparent = Color(0x00_00_00_00)

        val Red =         Color(0xFF_FF_00_00)
        val Green =       Color(0xFF_00_FF_00)
        val Blue =        Color(0xFF_00_00_FF)
        val Yellow =      Color(0xFF_FF_FF_00)
        val Cyan =        Color(0xFF_00_FF_FF)
        val Magenta =     Color(0xFF_FF_00_FF)

        private val hexFormat = HexFormat {
            upperCase = true
            number.prefix = "#"
        }
    }
}
