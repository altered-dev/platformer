package me.altered.platformer.action.effect

import androidx.compose.ui.util.lerp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.expression.Easing

@Serializable
@SerialName("rotate_by")
open class RotateBy(
    // TODO: to expression
    open val angle: Float,
    open val easing: Easing,
    override val duration: Float,
    override val target: Effect.Target,
) : Effect<Float> {

    override fun produce(time: Float): Float {
        if (time <= 0.0f) return 0.0f
        if (time >= duration) return angle
        return lerp(0.0f, angle, easing.ease(time / duration))
    }

    override fun toMutableEffect() = MutableRotateBy(
        angle = angle,
        easing = easing,
        duration = duration,
        target = target,
    )
}
