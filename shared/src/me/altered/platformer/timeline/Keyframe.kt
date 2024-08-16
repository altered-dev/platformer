package me.altered.platformer.timeline

import kotlinx.serialization.Serializable

@Serializable
data class Keyframe<T>(
    val time: Float,
    val value: Expression<T>,
    val easing: Easing = Easing.Linear,
)

infix fun <T> T.at(time: Float) = Keyframe(time, const(this))

infix fun <T> Expression<T>.at(time: Float) = Keyframe(time, this)

infix fun <T> Keyframe<T>.with(easing: Easing) = copy(easing = easing)
