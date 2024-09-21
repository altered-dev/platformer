package me.altered.platformer.expression

fun alerp(from: Float, to: Float, value: Float): Float {
    return (value - from) / (to - from)
}
