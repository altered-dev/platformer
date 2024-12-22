package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.altered.platformer.expression.AnimatedState
import me.altered.platformer.expression.Keyframe
import me.altered.platformer.level.data.MutableObject
import me.altered.platformer.level.node.MutableLevelNode
import me.altered.platformer.level.node.MutableObjectNode
import me.altered.platformer.scene.editor.state.SelectionState
import me.altered.platformer.state.TimelineState
import me.altered.platformer.ui.Text
import kotlin.math.roundToInt

@Composable
fun Timeline(
    level: MutableLevelNode,
    state: TimelineState,
    selectionState: SelectionState,
) {
    val lazyListState = rememberLazyListState()
    var offset by remember { mutableFloatStateOf(120.0f) }
    var step by remember { mutableFloatStateOf(80.0f) }
    val measurer = rememberTextMeasurer()
    val timePos by derivedStateOf { (state.roundedTime * step) + offset }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .width(256.dp)
                .fillMaxHeight(),
            state = lazyListState,
        ) {
            when (selectionState.selection.size) {
                0 -> item {
                    LevelItem(level)
                }
                else -> items(selectionState.selection, key = { it.obj.id }) { node ->
                    ObjectItem(node)
                }
            }
        }
        Box(
            modifier = Modifier
                .weight(1.0f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(4.dp))
                .border(1.dp, Color(0xFF262626), RoundedCornerShape(4.dp))
                .pan(
                    onPan = { offset += it.x },
                    onZoom = { delta, position, _ ->
                        val stepFrom = step
                        when {
                            delta.y > 0.0f -> step /= 1.0f + delta.y * 0.1f
                            delta.y < 0.0f -> step *= 1.0f - delta.y * 0.1f
                        }
                        offset += position.x * (1 - step / stepFrom)
                    },
                )
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        state.time = (state.time + dragAmount.x / step).coerceAtLeast(0.0f)
                    }
                },
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(),
            ) {
                var dx = (offset % step) - step
                while (dx < size.width) {
                    dx += step
                    val time = ((dx - offset) / step).roundToInt()
                    if (time < 0) continue
                    drawLine(Color(0xFF262626), Offset(dx, 0.0f), Offset(dx, size.height))
                    repeat(20) {
                        val x = dx + it * step / 20.0f
                        drawLine(Color(0xFF262626), Offset(x, 5.0f), Offset(x, size.height - 5.0f))
                    }
                    // TODO: this text lags for some reason, investigate
                    drawText(
                        textMeasurer = measurer,
                        text = time.toString(),
                        topLeft = Offset(dx - 12.0f, 61.0f),
                        size = Size(24.0f, 16.0f),
                        style = TextStyle(
                            color = Color(0xFFB2FFB2),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                        ),
                    )
                }
            }
            // separate canvas to not redraw text on timePos changes
            Canvas(
                modifier = Modifier.fillMaxSize(),
            ) {
                drawLine(Color(0xFFB2FFB2), Offset(timePos, 0.0f), Offset(timePos, size.height))
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                when (selectionState.selection.size) {
                    0 -> item {
                        LevelKeyframesItem(level, state, { offset }, { step })
                    }
                    else -> items(selectionState.selection, key = { it.obj.id }) { node ->
                        ObjectKeyframesItem(
                            node = node,
                            state = state,
                            offset = { offset },
                            step = { step },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LevelItem(
    level: MutableLevelNode,
    modifier: Modifier = Modifier,
) {
    ExpressionItem(level.level.background)
}

@Composable
private fun ObjectItem(
    node: MutableObjectNode,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(horizontal = 8.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InspectorIcon(node.inspectorInfo)
        Text(node.obj.name)
    }
    ExpressionItem(node.obj.x)
    ExpressionItem(node.obj.y)
    ExpressionItem(node.obj.width)
    ExpressionItem(node.obj.height)
    ExpressionItem(node.obj.rotation)
    (node.obj as? MutableObject.HasCornerRadius)?.let { obj ->
        ExpressionItem(obj.cornerRadius)
    }
    (node.obj as? MutableObject.HasFill)?.fill?.forEach { fill ->
        ExpressionItem(fill)
    }
    (node.obj as? MutableObject.HasStroke)?.let { obj ->
        obj.stroke.forEach { stroke ->
            ExpressionItem(stroke)
        }
        ExpressionItem(obj.strokeWidth)
    }
}

@Composable
private fun ExpressionItem(
    expression: AnimatedState<*>,
    modifier: Modifier = Modifier,
) {
    if (expression.keyframes.isEmpty()) return
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(start = 24.dp, end = 8.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InspectorIcon(expression.inspectorInfo)
        Text(expression.inspectorInfo.name)
    }
}

@Composable
private fun LevelKeyframesItem(
    level: MutableLevelNode,
    state: TimelineState,
    offset: () -> Float,
    step: () -> Float,
    modifier: Modifier = Modifier,
) {
    KeyframesItem(level.level.background, state, offset, step)
}

@Composable
private fun ObjectKeyframesItem(
    node: MutableObjectNode,
    state: TimelineState,
    offset: () -> Float,
    step: () -> Float,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = Modifier.height(36.dp).then(modifier))
    KeyframesItem(node.obj.x, state, offset, step)
    KeyframesItem(node.obj.y, state, offset, step)
    KeyframesItem(node.obj.width, state, offset, step)
    KeyframesItem(node.obj.height, state, offset, step)
    KeyframesItem(node.obj.rotation, state, offset, step)
    (node.obj as? MutableObject.HasCornerRadius)?.let { obj ->
        KeyframesItem(obj.cornerRadius, state, offset, step)
    }
    (node.obj as? MutableObject.HasFill)?.fill?.forEach { fill ->
        KeyframesItem(fill, state, offset, step)
    }
    (node.obj as? MutableObject.HasStroke)?.let { obj ->
        obj.stroke.forEach { stroke ->
            KeyframesItem(stroke, state, offset, step)
        }
        KeyframesItem(obj.strokeWidth, state, offset, step)
    }
}

@Composable
private fun <T> KeyframesItem(
    expression: AnimatedState<T>,
    state: TimelineState,
    offset: () -> Float,
    step: () -> Float,
    modifier: Modifier = Modifier,
) {
    if (expression.keyframes.isEmpty()) return
    Box(
        modifier = Modifier.height(36.dp).then(modifier),
        contentAlignment = Alignment.CenterStart,
    ) {
        expression.keyframes.forEach { kf ->
            Keyframe(
                keyframe = kf,
                offset = offset,
                step = step,
                onMove = { delta ->
                    expression.replaceFrame(kf, kf.copy(time = kf.time + delta))
                    expression.eval(state.time)
                },
            )
        }
    }
}

@Composable
private fun <T> Keyframe(
    keyframe: Keyframe<T>,
    offset: () -> Float,
    step: () -> Float,
    onMove: (delta: Float) -> Unit,
) {
    var deltaOffset by remember(keyframe) { mutableFloatStateOf(0.0f) }
    Spacer(
        modifier = Modifier
            .size(9.dp)
            .graphicsLayer {
                translationX = (keyframe.time * step()) + offset() - 4.0f + deltaOffset
                shape = CircleShape
                clip = true
            }
            .background(Color(0xFFB2FFB2))
            .pointerHoverIcon(PointerIcon.Hand)
            .pointerInput(keyframe) {
                detectDragGestures(
                    onDragEnd = {
                        onMove(deltaOffset / step())
                        deltaOffset = 0.0f
                    },
                    onDrag = { _, dragAmount ->
                        deltaOffset += dragAmount.x
                    }
                )
            }
    )
}
