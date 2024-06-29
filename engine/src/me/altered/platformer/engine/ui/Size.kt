package me.altered.platformer.engine.ui

sealed interface Size {

    data class Fixed(val value: Float) : Size

    data class Wrap(val min: Float, val max: Float) : Size

    data class Expand(val min: Float, val max: Float) : Size
}

val Number.px: Size
    get() = Size.Fixed(toFloat())

fun wrap(min: Float = 0.0f, max: Float = Float.POSITIVE_INFINITY): Size = Size.Wrap(min, max)

fun expand(min: Float = 0.0f, max: Float = Float.POSITIVE_INFINITY): Size = Size.Expand(min, max)

val wrap: Size
    get() = wrap()

val expand: Size
    get() = expand()
