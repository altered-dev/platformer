package me.altered.platformer.expression

import androidx.compose.ui.util.lerp

class AnimatedFloatState(
    initial: Expression<Float>,
    keyframes: List<Keyframe<Float>> = emptyList(),
) : AnimatedState<Float>(initial, keyframes) {

    override fun animate(from: Float, to: Float, t: Float): Float = lerp(from, to, t)

    override fun toAnimated() = AnimatedFloat(keyframes)
}

fun Expression<Float>.toAnimatedFloatState() = when (this) {
    is AnimatedFloatState -> this
    is AnimatedFloat -> AnimatedFloatState(keyframes.first().value, keyframes)
    else -> AnimatedFloatState(this, emptyList())
}
