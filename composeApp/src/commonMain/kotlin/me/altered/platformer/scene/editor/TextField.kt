package me.altered.platformer.scene.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun FloatTextField(
    state: AnimatedFloatState,
    timelineState: TimelineState,
    modifier: Modifier = Modifier,
    icon: @Composable BoxScope.() -> Unit = {},
) {
    var floatState by remember(state.staticValue) { mutableFloatStateOf(state.staticValue.round()) }
    TextField(
        value = floatState.toString(),
        onValueChange = { value -> value.toFloatOrNull()?.let { floatState = it } },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                state.staticValue = floatState
                defaultKeyboardAction(ImeAction.Done)
            },
        ),
        singleLine = true,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerHoverIcon(PointerIcon.Hand)
                .pointerInput(state) {
                    detectHorizontalDragGestures e@{ _, delta ->
                        floatState = (floatState + delta / 100.0f).round()
                        state.staticValue = floatState
                    }
                }
                .pointerInput(state) {
                    detectTapGestures(
                        onDoubleTap = {
                            state.addFrame(Keyframe(timelineState.roundedTime, const(state.staticValue)))
                        },
                    )
                }
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
            expr?.staticValue = value
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
