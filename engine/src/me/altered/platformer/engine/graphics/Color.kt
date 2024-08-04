package me.altered.platformer.engine.graphics

import kotlin.jvm.JvmInline

@JvmInline
value class Color(val value: Int) {

    val a: Int get() = value shr 24 and 0xFF

    val r: Int get() = value shr 16 and 0xFF

    val g: Int get() = value shr 8 and 0xFF

    val b: Int get() = value and 0xFF

    companion object {

        // TODO: to color object
        const val White =       0xFF_FF_FF_FF
        const val Black =       0xFF_00_00_00
        const val Transparent = 0x00_00_00_00

        const val Red =         0xFF_FF_00_00
        const val Green =       0xFF_00_FF_00
        const val Blue =        0xFF_00_00_FF
        const val Yellow =      0xFF_FF_FF_00
        const val Cyan =        0xFF_00_FF_FF
        const val Magenta =     0xFF_FF_00_FF
    }
}

fun Color(value: Long) = Color(value.toInt())

fun Color(r: Int, g: Int, b: Int, a: Int = 255): Color {
    require(a in 0..255) { "alpha is out of 0..255 range: $a" }
    require(r in 0..255) { "red is out of 0..255 range: $a" }
    require(g in 0..255) { "green is out of 0..255 range: $a" }
    require(b in 0..255) { "blue is out of 0..255 range: $a" }
    return Color(
            a and 0xFF shl 24
        or (r and 0xFF shl 16)
        or (g and 0xFF shl 8)
        or (b and 0xFF)
    )
}
