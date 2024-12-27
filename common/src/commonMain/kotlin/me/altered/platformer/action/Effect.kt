package me.altered.platformer.action

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import me.altered.platformer.expression.Easing

/**
 * A unit of operation of an action.
 */
@Serializable
@SerialName("effect")
sealed interface Effect<out T : Any> {

    // TODO: to expression
    val duration: Float
    val target: Target

    fun produce(time: Float): T

    /**
     * The object that the effect will be performed on.
     */
    @Serializable
    @SerialName("target")
    sealed interface Target {

        /**
         * The object that contains this action.
         */
        @Serializable
        @SerialName("self")
        data object Self : Target
    }
}

@Serializable
@SerialName("move_by")
data class MoveBy(
    // TODO: to expression
    val x: Float,
    val y: Float,
    val easing: Easing,
    override val duration: Float,
    override val target: Effect.Target,
) : Effect<Offset> {

    @Transient
    private val offset = Offset(x, y)

    override fun produce(time: Float): Offset {
        if (time <= 0.0f) return Offset.Zero
        if (time >= duration) return offset
        return lerp(Offset.Zero, offset, easing.ease(time / duration))
    }
}

@Serializable
@SerialName("rotate_by")
data class RotateBy(
    // TODO: to expression
    val angle: Float,
    val easing: Easing,
    override val duration: Float,
    override val target: Effect.Target,
) : Effect<Float> {

    override fun produce(time: Float): Float {
        if (time <= 0.0f) return 0.0f
        if (time >= duration) return angle
        return androidx.compose.ui.util.lerp(0.0f, angle, easing.ease(time / duration))
    }
}
