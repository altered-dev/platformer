package me.altered.platformer.engine.ui

sealed interface Size {

    data class Fixed(val value: Float) : Size

    data class Wrap(val min: Float, val max: Float) : Size

    data class Expand(val fraction: Float, val min: Float, val max: Float) : Size
}

/**
 * Creates a size that is always equal to the number.
 */
val Number.px: Size
    get() = Size.Fixed(toFloat())

/**
 * Creates a size that tries to be as small as possible within [min], [max] and children sizes.
 */
fun wrap(min: Float = 0.0f, max: Float = Float.POSITIVE_INFINITY): Size = Size.Wrap(min, max)

/**
 * Creates a size that tries to be as large as possible within [min], [max] and parent sizes.
 */
fun expand(
    fraction: Float = 1.0f,
    min: Float = 0.0f,
    max: Float = Float.POSITIVE_INFINITY,
): Size = Size.Expand(fraction, min, max)
