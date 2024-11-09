package me.altered.platformer.scene.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.altered.platformer.expression.AnimatedState
import me.altered.platformer.level.data.Rectangle
import kotlin.math.roundToInt

@Composable
fun Timeline(
    state: TimelineState,
    selectionState: SelectionState,
) {
    var offset by remember { mutableFloatStateOf(120.0f) }
    var step by remember { mutableFloatStateOf(80.0f) }
    val measurer = rememberTextMeasurer()
    val timePos by derivedStateOf { (state.roundedTime * step) + offset }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(vertical = 8.dp)
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
            selectionState.selection.forEach { node ->
                val obj = node.obj ?: return@forEach
                (obj.x as? AnimatedState<Float>)?.let { drawKeyframes(it, offset, 10.0f, timePos, step) }
                (obj.y as? AnimatedState<Float>)?.let { drawKeyframes(it, offset, 20.0f, timePos, step) }
                (obj.width as? AnimatedState<Float>)?.let { drawKeyframes(it, offset, 30.0f, timePos, step) }
                (obj.height as? AnimatedState<Float>)?.let { drawKeyframes(it, offset, 40.0f, timePos, step) }
                (obj.rotation as? AnimatedState<Float>)?.let { drawKeyframes(it, offset, 50.0f, timePos, step) }
                ((obj as? Rectangle)?.cornerRadius as? AnimatedState<Float>)?.let { drawKeyframes(it, offset, 60.0f, timePos, step) }
            }
            drawLine(Color(0xFFB2FFB2), Offset(timePos, 0.0f), Offset(timePos, size.height))
        }
    }
}

private fun DrawScope.drawKeyframes(
    state: AnimatedState<*>,
    offset: Float,
    y: Float,
    currentTime: Float,
    step: Float,
) {
    val positions = state.keyframes.map { (time) ->
        val x = (time * step) + offset
        val color = if (x == currentTime) Color(0xFFB2FFB2) else Color(0xFF999999)
        drawCircle(color, 3.5f, Offset(x, y))
        x
    }
    positions.windowed(2).forEach { (from, to) ->
        val color = if (currentTime in from..to) Color(0xFFB2FFB2) else Color(0xFF999999)
        drawLine(color, Offset(from + 5.0f, y), Offset(to - 5.0f, y))
    }
}
