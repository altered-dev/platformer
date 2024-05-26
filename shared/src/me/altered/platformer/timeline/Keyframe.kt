package me.altered.platformer.timeline

data class Keyframe<T>(
    val value: Expression<T>,
    val easing: Easing = Easing.linear,
)
