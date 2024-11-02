package me.altered.platformer.scene.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import me.altered.platformer.level.node.ObjectNode
import kotlin.math.max
import kotlin.math.min

@Composable
fun WorldOverlay(
    tool: Tool,
    hoveredNode: ObjectNode<*>?,
    hoveredBounds: Rect?,
    selected: List<Pair<ObjectNode<*>, Rect>>,
    modifier: Modifier,
    onDrag: (Offset) -> Unit = {},
    onResize: (delta: Offset, position: Offset, size: IntSize) -> Unit = { _, _, _ -> },
    onHover: (position: Offset, size: IntSize) -> Unit = { _, _ -> },
    onSelect: () -> Unit = {},
) {
    var selectionStart: Offset? by remember { mutableStateOf(null) }
    var selectionSize: Size? by remember { mutableStateOf(null) }

    val canvasModifier = when (tool) {
        Tool.Cursor -> Modifier.pointerEvents(
            onPrimaryStart = { offset ->
                selectionStart = offset
                selectionSize = Size.Zero
            },
            onPrimaryEnd = {
                selectionStart = null
                selectionSize = null
            },
            onPrimary = { offset ->
                selectionSize?.let { s -> selectionSize = Size(s.width + offset.x, s.height + offset.y) }
                // TODO: select objects
            },
            onTertiary = onDrag,
            onCtrlScroll = onResize,
            onMove = onHover,
        ).pointerInput(Unit) {
            detectTapGestures { onSelect() }
        }
        else -> Modifier
    }

    Canvas(
        modifier = modifier then canvasModifier,
    ) {
        run {
            val start = selectionStart ?: return@run
            val size = selectionSize ?: return@run
            drawRect(Color(0xFF0D99FF), start, size, 0.2f)
            drawRect(Color(0xFF0D99FF), start, size, 1.0f, Stroke(2.0f))
        }
        run {
            if (hoveredNode == null || hoveredBounds == null) return@run
            drawRect(Color(0xFF0D99FF), hoveredBounds.topLeft, hoveredBounds.size, style = Stroke())
        }
        run {
            if (selected.isEmpty()) return@run
            var left = Float.NEGATIVE_INFINITY
            var top = Float.NEGATIVE_INFINITY
            var right = Float.POSITIVE_INFINITY
            var bottom = Float.POSITIVE_INFINITY
            selected.forEach { (_, bounds) ->
                left = max(left, bounds.left)
                top = max(top, bounds.top)
                right = min(right, bounds.right)
                bottom = min(bottom, bounds.bottom)
            }
            println("selected bounds ${Rect(left, top, right, bottom)}")
            drawRect(Color.Blue, Offset(left, top), Size(right - left, bottom - top), style = Stroke())

        }
    }
}
