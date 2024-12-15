package me.altered.platformer.expression

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("keyframe")
data class Keyframe<T>(
    val time: Float,
    val value: Expression<T>,
    val easing: Easing = Easing.Linear,
)

infix fun <T> T.at(time: Float) = Keyframe(time, const(this))

infix fun Float.at(time: Float) = Keyframe(time, const(this))

infix fun <T> Expression<T>.at(time: Float) = Keyframe(time, this)

infix fun <T> Keyframe<T>.with(easing: Easing) = copy(easing = easing)
