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
private const val C4 = (2.0f * PI) / 3.0f
private const val C5 = (2.0f * PI) / 4.5f
private const val N1 = 7.5625f
private const val D1 = 2.75f

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
        val Linear = Easing { it }

        @JvmField
        val SineIn = Easing { 1.0f - cos(it * PI * 0.5f) }

        @JvmField
        val SineOut = Easing { sin(it * PI * 0.5f) }

        @JvmField
        val SineInOut = Easing { -(cos(it * PI) - 1) * 0.5f }

        @JvmField
        val QuadIn = Easing { it * it }

        @JvmField
        val QuadOut = Easing { 1.0f - (1.0f - it) * (1.0f - it) }

        @JvmField
        val QuadInOut = Easing { if (it < 0.5f) 2.0f * it * it else 1.0f - (-2.0f * it + 2.0f).pow(2) * 0.5f }

        @JvmField
        val CubicIn = Easing { it * it * it }

        @JvmField
        val CubicOut = Easing { 1.0f - (1.0f - it) * (1.0f - it) * (1.0f - it) }

        @JvmField
        val CubicInOut = Easing { if (it < 0.5f) 4.0f * it * it * it else 1.0f - (-2.0f * it + 2.0f).pow(3) * 0.5f }

        @JvmField
        val QuartIn = Easing { it * it * it * it }

        @JvmField
        val QuartOut = Easing { 1.0f - (1.0f - it).pow(4) }

        @JvmField
        val QuartInOut = Easing { if (it < 0.5f) 8.0f * it * it * it * it else 1.0f - (-2.0f * it + 2.0f).pow(4) * 0.5f }

        @JvmField
        val QuintIn = Easing { it * it * it * it * it }

        @JvmField
        val QuintOut = Easing { 1.0f - (1.0f - it).pow(5) }

        @JvmField
        val QuintInOut = Easing { if (it < 0.5f) 16.0f * it * it * it * it * it else 1.0f - (-2.0f * it + 2.0f).pow(5) * 0.5f }

        @JvmField
        val ExpoIn = Easing { if (it == 0.0f) 0.0f else 2.0f.pow(10.0f * it - 10.0f) }

        @JvmField
        val ExpoOut = Easing { if (it == 1.0f) 1.0f else 1.0f - 2.0f.pow(-10.0f * it) }

        @JvmField
        val ExpoInOut = Easing {
            when {
                it == 0.0f -> 0.0f
                it == 1.0f -> 1.0f
                it < 0.5f  -> 2.0f.pow(20.0f * it - 10.0f) * 0.5f
                else       -> (2.0f - 2.0f.pow(-20.0f * it + 10.0f)) * 0.5f
            }
        }

        @JvmField
        val CircIn = Easing { 1.0f - sqrt(1 - it * it) }

        @JvmField
        val CircOut = Easing { sqrt(1.0f - (1.0f - it) * (1.0f - it)) }

        @JvmField
        val CircInOut = Easing {
            when {
                it < 0.5f -> (1.0f - sqrt(1.0f - 4.0f * it * it)) * 0.5f
                else      -> (sqrt(1.0f - (-2.0f * it + 2.0f).pow(2)) + 1.0f) * 0.5f
            }
        }

        @JvmField
        val BackIn = Easing { C1 * it * it * it - C3 * it * it }

        @JvmField
        val BackOut = Easing { 1.0f + C3 * (it - 1.0f) * (it - 1.0f) * (it - 1.0f) + C1 * (it - 1.0f) * (it - 1.0f) }

        @JvmField
        val BackInOut = Easing {
            when {
                it < 0.5f -> ((2.0f * it).pow(2) * ((C2 + 1.0f) * 2.0f * it - C2)) * 0.5f
                else      -> ((2.0f * it - 2.0f).pow(2) * ((C2 + 1.0f) * (it * 2.0f - 2.0f) + C2) + 2.0f) * 0.5f
            }
        }

        @JvmField
        val ElasticIn = Easing {
            when (it) {
                0.0f, 1.0f -> it
                else       -> -(2.0f.pow(10.0f * it - 10.0f)) * sin((it * 10.0f - 10.75f) * C4)
            }
        }

        @JvmField
        val ElasticOut = Easing {
            when (it) {
                0.0f, 1.0f -> it
                else       -> 2.0f.pow(-10.0f * it) * sin((it * 10.0f - 0.75f) * C4) + 1.0f
            }
        }

        @JvmField
        val ElasticInOut = Easing {
            when {
                it == 0.0f || it == 1.0f -> it
                it < 0.5f -> -(2.0f.pow(20.0f * it - 10.0f) * sin((20.0f * it - 11.125f) * C5)) * 0.5f
                else      -> (2.0f.pow(-20.0f * it + 10.0f) * sin((20.0f * it - 11.125f) * C5)) * 0.5f + 1.0f
            }
        }

        @JvmField
        val BounceIn = Easing { 1.0f - BounceOut.ease(1.0f - it) }

        @JvmField
        val BounceOut = Easing {
            when {
                it < 1.0f / D1 -> N1 * it * it
                it < 2.0f / D1 -> N1 * (it - 1.5f / D1) * (it - 1.5f) + 0.75f
                it < 2.5f / D1 -> N1 * (it - 2.25f / D1) * (it - 2.25f) + 0.9375f
                else           -> N1 * (it - 2.625f / D1) * (it - 2.625f) + 0.984375f
            }
        }

        @JvmField
        val BounceInOut = Easing {
            when {
                it < 0.5f -> (1.0f - BounceOut.ease(1.0f - 2.0f * it)) * 0.5f
                else      -> (1.0f + BounceOut.ease(2.0f * it - 1.0f)) * 0.5f
            }
        }
    }
}
