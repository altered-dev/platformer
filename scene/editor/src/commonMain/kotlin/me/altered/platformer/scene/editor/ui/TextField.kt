package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.ImeAction
import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.expression.Keyframe
import me.altered.platformer.expression.const
import me.altered.platformer.state.TimelineState
import me.altered.platformer.ui.TextField
import me.altered.platformer.util.round

@Composable
fun FloatTextField(
    state: AnimatedFloatState,
    timelineState: TimelineState,
    modifier: Modifier = Modifier,
) {
    var floatState by remember(state.staticValue) { mutableFloatStateOf(state.staticValue.round()) }
    TextField(
        value = floatState.toString(),
        onValueChange = { value -> value.toFloatOrNull()?.let { floatState = it } },
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
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
            InspectorIcon(state.inspectorInfo)
        }
    }
}
