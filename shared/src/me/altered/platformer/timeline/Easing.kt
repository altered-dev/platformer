package me.altered.platformer.timeline

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * A set of smoothing functions for animation interpolation.
 *
 * NOTE: this is not an enum because there is a possibility of custom easings in the future.
 */
@Serializable
sealed interface Easing {

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

    /**
     * Applies the easing function onto [value], clamping it between 0 and 1 to avoid unexpected results.
     *
     * @see ease
     */
    fun easeSafe(value: Float): Float = ease(value.coerceIn(0.0f, 1.0f))

    @Serializable
    @SerialName("linear")
    data object Linear : Easing {
        override fun ease(value: Float): Float = value
    }

    @Serializable
    @SerialName("sine-in")
    data object SineIn : Easing {
        override fun ease(value: Float): Float = 1.0f - cos(value * PI * 0.5f)
    }

    @Serializable
    @SerialName("sine-out")
    data object SineOut : Easing {
        override fun ease(value: Float): Float = sin(value * PI * 0.5f)
    }

    @Serializable
    @SerialName("sine-inout")
    data object SineInOut : Easing {
        override fun ease(value: Float): Float = -(cos(value * PI) - 1) * 0.5f
    }

    @Serializable
    @SerialName("quad-in")
    data object QuadIn : Easing {
        override fun ease(value: Float): Float = value * value
    }

    @Serializable
    @SerialName("quad-out")
    data object QuadOut : Easing {
        override fun ease(value: Float): Float = 1.0f - (1.0f - value) * (1.0f - value)
    }

    @Serializable
    @SerialName("quad-inout")
    data object QuadInOut : Easing {
        override fun ease(value: Float): Float = when {
            value < 0.5f -> 2.0f * value * value
            else -> 1.0f - (-2.0f * value + 2.0f).pow(2) * 0.5f
        }
    }

    @Serializable
    @SerialName("cubic-in")
    data object CubicIn : Easing {
        override fun ease(value: Float): Float = value * value * value
    }

    @Serializable
    @SerialName("cubic-out")
    data object CubicOut : Easing {
        override fun ease(value: Float): Float = 1.0f - (1.0f - value) * (1.0f - value) * (1.0f - value)
    }

    @Serializable
    @SerialName("cubic-inout")
    data object CubicInOut : Easing {
        override fun ease(value: Float): Float = when {
            value < 0.5f -> 4.0f * value * value * value
            else -> 1.0f - (-2.0f * value + 2.0f).pow(3) * 0.5f
        }
    }

    @Serializable
    @SerialName("quart-in")
    data object QuartIn : Easing {
        override fun ease(value: Float): Float = value * value * value * value
    }

    @Serializable
    @SerialName("quart-out")
    data object QuartOut : Easing {
        override fun ease(value: Float): Float = 1.0f - (1.0f - value).pow(4)
    }

    @Serializable
    @SerialName("quart-inout")
    data object QuartInOut : Easing {
        override fun ease(value: Float): Float = when {
            value < 0.5f -> 8.0f * value * value * value * value
            else -> 1.0f - (-2.0f * value + 2.0f).pow(4) * 0.5f
        }
    }

    @Serializable
    @SerialName("quint-in")
    data object QuintIn : Easing {
        override fun ease(value: Float): Float = value * value * value * value * value
    }

    @Serializable
    @SerialName("quint-out")
    data object QuintOut : Easing {
        override fun ease(value: Float): Float = 1.0f - (1.0f - value).pow(5)
    }

    @Serializable
    @SerialName("quint-inout")
    data object QuintInOut : Easing {
        override fun ease(value: Float): Float = when {
            value < 0.5f -> 16.0f * value * value * value * value * value
            else -> 1.0f - (-2.0f * value + 2.0f).pow(5) * 0.5f
        }
    }

    @Serializable
    @SerialName("expo-in")
    data object ExpoIn : Easing {
        override fun ease(value: Float): Float = if (value == 0.0f) 0.0f else 2.0f.pow(10.0f * value - 10.0f)
    }

    @Serializable
    @SerialName("expo-out")
    data object ExpoOut : Easing {
        override fun ease(value: Float): Float = if (value == 1.0f) 1.0f else 1.0f - 2.0f.pow(-10.0f * value)
    }

    @Serializable
    @SerialName("expo-inout")
    data object ExpoInOut : Easing {
        override fun ease(value: Float): Float = when {
            value == 0.0f || value == 1.0f -> value
            value < 0.5f -> 2.0f.pow(20.0f * value - 10.0f) * 0.5f
            else -> (2.0f - 2.0f.pow(-20.0f * value + 10.0f)) * 0.5f
        }
    }

    @Serializable
    @SerialName("circ-in")
    data object CircIn : Easing {
        override fun ease(value: Float): Float = 1.0f - sqrt(1 - value * value)
    }

    @Serializable
    @SerialName("circ-out")
    data object CircOut : Easing {
        override fun ease(value: Float): Float = sqrt(1.0f - (1.0f - value) * (1.0f - value))
    }

    @Serializable
    @SerialName("circ-inout")
    data object CircInOut : Easing {
        override fun ease(value: Float): Float = when {
            value < 0.5f -> (1.0f - sqrt(1.0f - 4.0f * value * value)) * 0.5f
            else -> (sqrt(1.0f - (-2.0f * value + 2.0f).pow(2)) + 1.0f) * 0.5f
        }
    }

    @Serializable
    @SerialName("back-in")
    data object BackIn : Easing {
        override fun ease(value: Float): Float = C3 * value * value * value - C1 * value * value
    }

    @Serializable
    @SerialName("back-out")
    data object BackOut : Easing {
        override fun ease(value: Float): Float =
            1.0f + C3 * (value - 1.0f) * (value - 1.0f) * (value - 1.0f) + C1 * (value - 1.0f) * (value - 1.0f)
    }

    @Serializable
    @SerialName("back-inout")
    data object BackInOut : Easing {
        override fun ease(value: Float): Float = when {
            value < 0.5f -> ((2.0f * value).pow(2) * ((C2 + 1.0f) * 2.0f * value - C2)) * 0.5f
            else -> ((2.0f * value - 2.0f).pow(2) * ((C2 + 1.0f) * (value * 2.0f - 2.0f) + C2) + 2.0f) * 0.5f
        }
    }

    @Serializable
    @SerialName("elastic-in")
    data object ElasticIn : Easing {
        override fun ease(value: Float): Float = when (value) {
            0.0f, 1.0f -> value
            else -> -(2.0f.pow(10.0f * value - 10.0f)) * sin((value * 10.0f - 10.75f) * C4)
        }
    }

    @Serializable
    @SerialName("elastic-out")
    data object ElasticOut : Easing {
        override fun ease(value: Float): Float = when (value) {
            0.0f, 1.0f -> value
            else -> 2.0f.pow(-10.0f * value) * sin((value * 10.0f - 0.75f) * C4) + 1.0f
        }
    }

    @Serializable
    @SerialName("elastic-inout")
    data object ElasticInOut : Easing {
        override fun ease(value: Float): Float = when {
            value == 0.0f || value == 1.0f -> value
            value < 0.5f -> -(2.0f.pow(20.0f * value - 10.0f) * sin((20.0f * value - 11.125f) * C5)) * 0.5f
            else -> (2.0f.pow(-20.0f * value + 10.0f) * sin((20.0f * value - 11.125f) * C5)) * 0.5f + 1.0f
        }
    }

    @Serializable
    @SerialName("bounce-in")
    data object BounceIn : Easing {
        override fun ease(value: Float): Float = 1.0f - BounceOut.ease(1.0f - value)
    }

    @Serializable
    @SerialName("bounce-out")
    data object BounceOut : Easing {
        override fun ease(value: Float): Float = when {
            value < 1.0f / D1 -> N1 * value * value
            value < 2.0f / D1 -> {
                val x2 = value - (1.5f / D1)
                N1 * x2 * x2 + 0.75f
            }
            value < 2.5f / D1 -> {
                val x2 = value - (2.25f / D1)
                N1 * x2 * x2 + 0.9375f
            }
            else -> {
                val x2 = value - (2.625f / D1)
                N1 * x2 * x2 + 0.984375f
            }
        }
    }

    @Serializable
    @SerialName("bounce-inout")
    data object BounceInOut : Easing {
        override fun ease(value: Float): Float = when {
            value < 0.5f -> (1.0f - BounceOut.ease(1.0f - 2.0f * value)) * 0.5f
            else -> (1.0f + BounceOut.ease(2.0f * value - 1.0f)) * 0.5f
        }
    }

    private companion object {
        private const val PI = kotlin.math.PI.toFloat()
        private const val C1 = 1.70158f
        private const val C2 = C1 * 1.525f
        private const val C3 = C1 + 1.0f
        private const val C4 = (2.0f * PI) / 3.0f
        private const val C5 = (2.0f * PI) / 4.5f
        private const val N1 = 7.5625f
        private const val D1 = 2.75f
    }
}
