package me.altered.platformer.engine.math

const val PI = kotlin.math.PI.toFloat()

inline fun alerp(from: Float, to: Float, value: Float): Float {
    return (value - from) / (to - from)
}

inline fun Float.deg2rad(): Float {
    return this / 180.0f * PI
}

inline fun Float.rad2deg(): Float {
    return this / PI * 180.0f
}
