package me.altered.platformer.action.effect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import me.altered.platformer.expression.Easing
import me.altered.platformer.expression.InspectorInfo

class MutableMoveBy(
    x: Float = 0.0f,
    y: Float = 0.0f,
    easing: Easing = Easing.Linear,
    duration: Float = 0.0f,
    target: Effect.Target = Effect.Target.Self,
) : MoveBy(x, y, easing, duration, target), MutableEffect<Offset> {

    override val inspectorInfo: InspectorInfo
        get() = InspectorInfo.Move

    override var x by mutableFloatStateOf(x)
    override var y by mutableFloatStateOf(y)
    override var easing by mutableStateOf(easing)
    override var duration by mutableFloatStateOf(duration)
    override var target by mutableStateOf(target)

    override fun toEffect() = MoveBy(
        x = x,
        y = y,
        easing = easing,
        duration = duration,
        target = target,
    )

    override fun toMutableEffect() = this
}
