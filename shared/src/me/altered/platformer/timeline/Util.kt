package me.altered.platformer.timeline

fun alerp(from: Float, to: Float, value: Float): Float {
    return (value - from) / (to - from)
}
