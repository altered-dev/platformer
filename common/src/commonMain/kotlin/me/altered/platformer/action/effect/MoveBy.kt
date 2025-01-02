package me.altered.platformer.action.effect

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.expression.Easing

@Serializable
@SerialName("move_by")
open class MoveBy(
    // TODO: to expression
    open val x: Float,
    open val y: Float,
    open val easing: Easing,
    override val duration: Float,
    override val target: Effect.Target,
) : Effect<Offset> {

    override fun produce(time: Float): Offset {
        if (time <= 0.0f) return Offset.Companion.Zero
        if (time >= duration) return Offset(x, y)
        return lerp(Offset.Companion.Zero, Offset(x, y), easing.ease(time / duration))
    }

    override fun toMutableEffect() = MutableMoveBy(
        x = x,
        y = y,
        easing = easing,
        duration = duration,
        target = target,
    )
}
