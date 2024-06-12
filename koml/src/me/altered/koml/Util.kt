package me.altered.koml

const val PI = kotlin.math.PI.toFloat()

fun Float.deg2rad(): Float {
    return this / 180.0f * PI
}

fun Float.rad2deg(): Float {
    return this / PI * 180.0f
}
