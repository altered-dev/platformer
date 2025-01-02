package me.altered.platformer.action.effect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import me.altered.platformer.expression.Easing
import me.altered.platformer.expression.InspectorInfo

class MutableRotateBy(
    angle: Float = 0.0f,
    easing: Easing = Easing.Linear,
    duration: Float = 0.0f,
    target: Effect.Target = Effect.Target.Self,
) : RotateBy(angle, easing, duration, target), MutableEffect<Float> {

    override val inspectorInfo: InspectorInfo
        get() = InspectorInfo.Rotate

    override var angle by mutableFloatStateOf(angle)
    override var easing by mutableStateOf(easing)
    override var duration by mutableFloatStateOf(duration)
    override var target by mutableStateOf(target)

    override fun toEffect() = RotateBy(
        angle = angle,
        easing = easing,
        duration = duration,
        target = target,
    )

    override fun toMutableEffect() = this
}
