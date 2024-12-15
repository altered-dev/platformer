package me.altered.platformer.util

import kotlin.math.roundToInt

fun Float.round() = (this * 100.0f).roundToInt() / 100.0f

fun <T> Iterable<T>.medianOf(transform: (T) -> Float): Float {
    var min = Float.POSITIVE_INFINITY
    var max = Float.NEGATIVE_INFINITY

    forEach {
        val value = transform(it)
        min = minOf(min, value)
        max = maxOf(max, value)
    }

    return (min + max) / 2.0f
}
