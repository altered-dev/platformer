package me.altered.platformer.engine.math

import androidx.compose.runtime.Stable

const val PI = kotlin.math.PI.toFloat()

@Stable
fun alerp(from: Float, to: Float, value: Float): Float {
    return (value - from) / (to - from)
}

/**
 * Linearly interpolates [t] between [from] and [to].
 *
 * @param from the lower bound
 * @param to the upper bound
 * @param t the lerp value, usually between 0 and 1
 *
 * @return [from] if [t] is 0, [to] if [t] is 1, everything in between otherwise
 */
@Stable
fun lerp(from: Float, to: Float, t: Float): Float {
    return from + t * (to - from)
}

@Stable
fun Float.deg2rad(): Float {
    return this / 180.0f * PI
}

@Stable
fun Float.rad2deg(): Float {
    return this / PI * 180.0f
}
