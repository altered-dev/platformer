@file:OptIn(ExperimentalStdlibApi::class)

package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.altered.platformer.expression.AnimatedBrushState
import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.expression.Keyframe
import me.altered.platformer.expression.const
import me.altered.platformer.level.data.Brush
import me.altered.platformer.level.data.solid
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
                    coroutineScope {
                        launch {
                            detectHorizontalDragGestures e@{ _, delta ->
                                floatState = (floatState + delta / 100.0f).round()
                                state.staticValue = floatState
                            }
                        }
                        launch {
                            detectTapGestures(
                                onDoubleTap = {
                                    state.addFrame(Keyframe(timelineState.roundedTime, const(state.staticValue)))
                                },
                            )
                        }
                    }
                }
        ) {
            InspectorIcon(state.inspectorInfo)
        }
    }
}

@Composable
fun BrushTextField(
    state: AnimatedBrushState,
    timelineState: TimelineState,
    modifier: Modifier = Modifier,
) {
    // for now only supports solid brushes
    var hexState by remember(state.staticValue) {
        val solid = state.staticValue as? Brush.Solid
        mutableStateOf((solid?.color?.value ?: 0).toHexString(HexFormat))
    }
    TextField(
        value = hexState,
        onValueChange = { hexState = it },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                val color = try {
                    hexState.hexToInt(HexFormat)
                } catch (_: Exception) {
                    0
                }
                state.staticValue = solid(color)
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
                    detectTapGestures(
                        onDoubleTap = {
                            state.addFrame(Keyframe(timelineState.roundedTime, const(state.staticValue)))
                        }
                    )
                }
        ) {
            val color = try {
                hexState.hexToInt(HexFormat)
            } catch (_: Exception) {
                0
            }
            Spacer(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color(color), RoundedCornerShape(4.dp))
            )
        }
    }
}

private val HexFormat = HexFormat {
    upperCase = true
}