package me.altered.platformer.scene.editor

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import me.altered.platformer.expression.AnimatedBrushState
import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.expression.Expression
import me.altered.platformer.expression.Keyframe
import me.altered.platformer.expression.const
import me.altered.platformer.level.data.Brush
import me.altered.platformer.level.data.Object
import me.altered.platformer.level.node.ObjectNode

fun <O : Object, N : ObjectNode<O>> Modifier.floatKeyframe(
    state: TimelineState,
    node: N,
    expression: O.() -> Expression<Float>,
    value: N.() -> Float,
) = pointerInput(node.id) {
    detectTapGestures(
        onDoubleTap = {
            (node.obj?.expression() as? AnimatedFloatState)?.addFrame(
                Keyframe(
                    time = state.roundedTime,
                    value = const(node.value()),
                )
            )
        }
    )
}

fun <O : Object, N : ObjectNode<O>> Modifier.brushKeyframe(
    state: TimelineState,
    node: N,
    expression: O.() -> Expression<Brush>,
    value: N.() -> Brush,
) = pointerInput(node.id) {
    detectTapGestures(
        onDoubleTap = {
            (node.obj?.expression() as? AnimatedBrushState)?.addFrame(
                Keyframe(
                    time = state.roundedTime,
                    value = const(node.value()),
                ).also { println(it) }
            )
        }
    )
}
