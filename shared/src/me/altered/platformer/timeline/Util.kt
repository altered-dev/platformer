package me.altered.platformer.timeline

fun alerp(from: Float, to: Float, value: Float): Float {
    return (value - from) / (to - from)
}

infix fun <T> T.at(time: Float) = time to Keyframe(const(this))

infix fun <T> Expression<T>.at(time: Float) = time to Keyframe(this)

infix fun <T> Pair<Float, Keyframe<T>>.with(easing: Easing) = first to second.copy(easing = easing)
