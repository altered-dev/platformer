package me.altered.platformer.expression

import androidx.compose.ui.util.lerp
import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.level.data.Brush
import me.altered.platformer.level.data.solid

class AnimatedFloatState(
    initial: Float,
    keyframes: List<Keyframe<Float>> = emptyList(),
) : AnimatedState<Float>(initial, keyframes) {

    override fun animate(from: Float, to: Float, t: Float): Float = lerp(from, to, t)

    override fun toAnimated() = AnimatedFloat(keyframes)
}

fun Expression<Float>.toAnimatedFloatState() = when (this) {
    is AnimatedFloatState -> this
    is AnimatedFloat -> AnimatedFloatState(eval(0.0f), keyframes)
    else -> AnimatedFloatState(eval(0.0f), listOf(Keyframe(0.0f, this)))
}

class AnimatedBrushState(
    initial: Brush,
    keyframes: List<Keyframe<Brush>> = emptyList(),
) : AnimatedState<Brush>(initial, keyframes) {

    override fun animate(from: Brush, to: Brush, t: Float): Brush {
        // TODO: support gradient animations
        if (from !is Brush.Solid || to !is Brush.Solid) return solid(Color.Transparent)
        return solid(from.color.lerp(to.color, t))
    }

    override fun toAnimated() = AnimatedBrush(keyframes)
}

fun Expression<Brush>.toAnimatedBrushState() = when (this) {
    is AnimatedBrushState -> this
    is AnimatedBrush -> AnimatedBrushState(eval(0.0f), keyframes)
    else -> AnimatedBrushState(eval(0.0f), listOf(Keyframe(0.0f, this)))
}
