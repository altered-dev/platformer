package me.altered.platformer.timeline

class AnimatedFloat(
    timeline: Timeline,
    vararg keyframes: Pair<Float, Keyframe<Float>>,
) : Expression.Animated<Float>(timeline, *keyframes) {

    override fun animate(from: Float, to: Float, time: Float, easing: Easing): Float {
        return lerp(from, to, time, easing)
    }
}
