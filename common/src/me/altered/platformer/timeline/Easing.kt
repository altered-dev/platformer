package me.altered.platformer.timeline

import kotlin.jvm.JvmField
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

private const val PI = kotlin.math.PI.toFloat()

private const val C1 = 1.70158f
private const val C2 = C1 * 1.525f
private const val C3 = C1 + 1.0f

/**
 * A set of smoothing functions for animation interpolation.
 */
fun interface Easing {

    /**
     * Applies the easing function onto [value].
     *
     * @param value a number from 0 to 1 (inclusive).
     *
     * @return the eased value.
     * Whenever 0 or 1 are passed, it should return those numbers respectively.
     * The values in between are not guaranteed to be between 0 and 1.
     */
    fun ease(value: Float): Float

    fun easeSafe(value: Float): Float = ease(value.coerceIn(0.0f, 1.0f))

    companion object {

        @JvmField
        val linear = Easing { it }

        @JvmField
        val sineIn = Easing { 1.0f - cos(it * PI * 0.5f) }

        @JvmField
        val sineOut = Easing { sin(it * PI * 0.5f) }

        @JvmField
        val sineInOut = Easing { -(cos(it * PI) - 1) * 0.5f }

        @JvmField
        val quadIn = Easing { it * it }

        @JvmField
        val quadOut = Easing { 1.0f - (1.0f - it) * (1.0f - it) }

        @JvmField
        val quadInOut = Easing { if (it < 0.5f) 2.0f * it * it else 1.0f - (-2.0f * it + 2.0f).pow(2) * 0.5f }

        @JvmField
        val cubicIn = Easing { it * it * it }

        @JvmField
        val cubicOut = Easing { 1.0f - (1.0f - it) * (1.0f - it) * (1.0f - it) }

        @JvmField
        val cubicInOut = Easing { if (it < 0.5f) 4.0f * it * it * it else 1.0f - (-2.0f * it + 2.0f).pow(3) * 0.5f }

        @JvmField
        val quartIn = Easing { it * it * it * it }

        @JvmField
        val quartOut = Easing { 1.0f - (1.0f - it).pow(4) }

        @JvmField
        val quartInOut = Easing { if (it < 0.5f) 8.0f * it * it * it * it else 1.0f - (-2.0f * it + 2.0f).pow(4) * 0.5f }

        @JvmField
        val quintIn = Easing { it * it * it * it * it }

        @JvmField
        val quintOut = Easing { 1.0f - (1.0f - it).pow(5) }

        @JvmField
        val quintInOut = Easing { if (it < 0.5f) 16.0f * it * it * it * it * it else 1.0f - (-2.0f * it + 2.0f).pow(5) * 0.5f }

        @JvmField
        val expoIn = Easing { if (it == 0.0f) 0.0f else 2.0f.pow(10.0f * it - 10.0f) }

        @JvmField
        val expoOut = Easing { if (it == 1.0f) 1.0f else 1.0f - 2.0f.pow(-10.0f * it) }

        @JvmField
        val expoInOut = Easing {
            when {
                it == 0.0f -> 0.0f
                it == 1.0f -> 1.0f
                it < 0.5f -> 2.0f.pow(20.0f * it - 10.0f) * 0.5f
                else -> (2.0f - 2.0f.pow(-20.0f * it + 10.0f)) * 0.5f
            }
        }

        @JvmField
        val circIn = Easing { 1.0f - sqrt(1 - it * it) }

        @JvmField
        val circOut = Easing { sqrt(1.0f - (1.0f - it) * (1.0f - it)) }

        @JvmField
        val circInOut = Easing {
            when {
                it < 0.5f -> (1.0f - sqrt(1.0f - 4.0f * it * it)) * 0.5f
                else -> (sqrt(1.0f - (-2.0f * it + 2.0f).pow(2)) + 1.0f) * 0.5f
            }
        }

        @JvmField
        val backIn = Easing { C1 * it * it * it - C3 * it * it }

        @JvmField
        val backOut = Easing { 1.0f + C3 * (it - 1.0f) * (it - 1.0f) * (it - 1.0f) + C1 * (it - 1.0f) * (it - 1.0f) }

//        val backInOut = Easing {
//            when {
//                it < 0.5f ->
//            }
//        }
    }
}
