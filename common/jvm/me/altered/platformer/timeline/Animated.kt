package me.altered.platformer.timeline

import org.jetbrains.skia.Color4f

class AnimatedFloat(
    timeline: Timeline,
    vararg keyframes: Pair<Float, Keyframe<Float>>,
) : Expression.Animated<Float>(timeline, *keyframes) {

    override fun animate(from: Float, to: Float, time: Float, easing: Easing): Float {
        return lerp(from, to, time, easing)
    }
}

fun Timeline.animated(
    vararg keyframes: Pair<Float, Keyframe<Float>>,
) = AnimatedFloat(this, *keyframes)

class AnimatedColor(
    timeline: Timeline,
    vararg keyframes: Pair<Float, Keyframe<Color4f>>,
) : Expression.Animated<Color4f>(timeline, *keyframes) {

    override fun animate(from: Color4f, to: Color4f, time: Float, easing: Easing): Color4f {
        return lerp(from, to, time, easing)
    }
}

fun Timeline.animated(
    vararg keyframes: Pair<Float, Keyframe<Color4f>>,
) = AnimatedColor(this, *keyframes)
