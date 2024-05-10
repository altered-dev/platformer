package me.altered.platformer.timeline

import io.github.humbleui.skija.Color4f
import org.joml.Vector2f
import org.joml.Vector2fc

// region easing

fun Easing.easeSafe(value: Float) = ease(value.coerceIn(0.0f, 1.0f))

// endregion

// region expressions

fun <T> const(value: T) = Expression.Constant(value)

fun <T> expression(block: () -> T) = Expression.Function(block)

fun lerp(from: Float, to: Float, t: Float, easing: Easing = Easing.linear): Float {
    return from + easing.easeSafe(t) * (to - from)
}

fun lerp(
    from: Vector2fc,
    to: Vector2fc,
    t: Float,
    easing: Easing = Easing.linear,
    dest: Vector2f = Vector2f(),
): Vector2f = from.lerp(to, easing.easeSafe(t), dest)

fun lerp(
    from: Color4f,
    to: Color4f,
    t: Float,
    easing: Easing = Easing.linear,
): Color4f = from.makeLerp(to, easing.easeSafe(t))

fun alerp(from: Float, to: Float, value: Float): Float {
    return (value - from) / (to - from)
}

// endregion

// region keyframes

infix fun <T> T.at(time: Float) = time to Keyframe(const(this))

infix fun <T> Expression<T>.at(time: Float) = time to Keyframe(this)

infix fun <T> Pair<Float, Keyframe<T>>.with(easing: Easing) = first to Keyframe(second.value, easing)

// endregion
