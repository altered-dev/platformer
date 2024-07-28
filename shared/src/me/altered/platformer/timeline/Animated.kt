package me.altered.platformer.timeline

import me.altered.koml.Vector2f
import me.altered.koml.lerp
import me.altered.platformer.util.KeyframeList
import org.jetbrains.skia.Color4f

class AnimatedFloat(
    keyframes: KeyframeList<Float>,
) : Expression.Animated<Float>(keyframes) {

    override fun animate(from: Float, to: Float, t: Float): Float {
        return lerp(from, to, t)
    }
}

fun animated(
    vararg keyframes: Pair<Float, Keyframe<Float>>,
): Expression<Float> = AnimatedFloat(KeyframeList(keyframes))

class AnimatedColor4f(
    keyframes: KeyframeList<Color4f>,
) : Expression.Animated<Color4f>(keyframes) {

    override fun animate(from: Color4f, to: Color4f, t: Float): Color4f {
        return from.makeLerp(to, t)
    }
}

fun animated(
    vararg keyframes: Pair<Float, Keyframe<Color4f>>,
): Expression<Color4f> = AnimatedColor4f(KeyframeList(keyframes))

class AnimatedVector2f(
    keyframes: KeyframeList<Vector2f>,
) : Expression.Animated<Vector2f>(keyframes) {

    override fun animate(from: Vector2f, to: Vector2f, t: Float): Vector2f {
        return from.lerp(to, t)
    }
}

fun animated(
    vararg keyframes: Pair<Float, Keyframe<Vector2f>>,
): Expression<Vector2f> = AnimatedVector2f(KeyframeList(keyframes))
