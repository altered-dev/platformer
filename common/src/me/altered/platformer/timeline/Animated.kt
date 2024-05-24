package me.altered.platformer.timeline

import me.altered.koml.Vector2f
import me.altered.platformer.util.KeyframeList
import org.jetbrains.skia.Color4f

class AnimatedFloat(
    timeline: Timeline,
    keyframes: KeyframeList<Float>,
) : Expression.Animated<Float>(timeline, keyframes) {

    override fun animate(from: Float, to: Float, t: Float): Float {
        return from + t * (to - from)
    }
}

fun Timeline.animated(
    vararg keyframes: Pair<Float, Keyframe<Float>>,
) = AnimatedFloat(this, KeyframeList(keyframes))

class AnimatedColor4f(
    timeline: Timeline,
    keyframes: KeyframeList<Color4f>,
) : Expression.Animated<Color4f>(timeline, keyframes) {

    override fun animate(from: Color4f, to: Color4f, t: Float): Color4f {
        return from.makeLerp(to, t)
    }
}

fun Timeline.animated(
    vararg keyframes: Pair<Float, Keyframe<Color4f>>,
) = AnimatedColor4f(this, KeyframeList(keyframes))

class AnimatedVector2f(
    timeline: Timeline,
    keyframes: KeyframeList<Vector2f>,
) : Expression.Animated<Vector2f>(timeline, keyframes) {

    override fun animate(from: Vector2f, to: Vector2f, t: Float): Vector2f {
        return from.lerp(to, t)
    }
}

fun Timeline.animated(
    vararg keyframes: Pair<Float, Keyframe<Vector2f>>,
) = AnimatedVector2f(this, KeyframeList(keyframes))
