package me.altered.platformer.scene.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import me.altered.platformer.expression.AnimatedBrushState
import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.expression.Expression
import me.altered.platformer.expression.Keyframe
import me.altered.platformer.expression.const
import me.altered.platformer.level.data.Brush
import me.altered.platformer.level.data.Object
import me.altered.platformer.level.data.solid
import me.altered.platformer.level.data.toComposeBrush
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.ui.TextField

@Composable
fun <O : Object, N : ObjectNode<O>> FloatTextField(
    node: N,
    expression: O.() -> Expression<Float>,
    value: N.() -> Float,
    onValueChanged: N.(Float) -> Unit,
    timelineState: TimelineState,
    modifier: Modifier = Modifier,
    icon: @Composable BoxScope.() -> Unit = {},
) {
    val state = remember(node, timelineState.roundedTime) { TextFieldState(node.value().toString()) }
    val expr = node.obj?.expression() as? AnimatedFloatState

    TextField(
        state = state,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        onKeyboardAction = e@{
            val value = state.text.toString().toFloatOrNull() ?: return@e
            node.onValueChanged(value)
            expr?.staticValue = const(value)
            it()
        },
        lineLimits = TextFieldLineLimits.SingleLine,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerHoverIcon(PointerIcon.Hand)
                .pointerInput(node.id) {
                    detectHorizontalDragGestures e@{ _, delta ->
                        val value = (state.text.toString().toFloatOrNull() ?: return@e) + delta / 100.0f
                        node.onValueChanged(value)
                        expr?.staticValue = const(value)
                        state.setTextAndPlaceCursorAtEnd(value.toString())
                    }
                }
                .pointerInput(node.id) {
                    detectTapGestures(
                        onDoubleTap = {
                            expr?.addFrame(Keyframe(timelineState.roundedTime, const(node.value())))
                        }
                    )
                },
            contentAlignment = Alignment.Center,
        ) {
            icon()
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun <O : Object, N : ObjectNode<O>> ColorTextField(
    node: N,
    expression: O.() -> Expression<Brush>,
    value: N.() -> Brush,
    onValueChanged: N.(Brush) -> Unit,
    timelineState: TimelineState,
    modifier: Modifier = Modifier,
) {
    val state = remember(node.id, timelineState.roundedTime) { TextFieldState(node.value().toText()) }
    val expr = node.obj?.expression() as? AnimatedBrushState

    TextField(
        state = state,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        onKeyboardAction = e@{
            val value = solid(runCatching { state.text.toString().hexToInt(HexFormat) }.getOrElse { return@e })
            node.onValueChanged(value)
            expr?.staticValue = const(value)
            it()
        },
        lineLimits = TextFieldLineLimits.SingleLine,
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(2.dp))
                .background(node.value().toComposeBrush())
                .pointerHoverIcon(PointerIcon.Hand)
                .pointerInput(node.id) {
                    detectTapGestures(
                        onDoubleTap = {
                            expr?.addFrame(Keyframe(timelineState.roundedTime, const(node.value())))
                        }
                    )
                },
        )
    }
}

@OptIn(ExperimentalStdlibApi::class)
private fun Brush.toText() = when (this) {
    is Brush.Solid -> color.value.toHexString(HexFormat)
    is Brush.Linear -> "Linear"
    is Brush.Radial -> "Radial"
    is Brush.Sweep -> "Sweep"
}

@OptIn(ExperimentalStdlibApi::class)
private val HexFormat = HexFormat {
    upperCase = true
}
