package me.altered.platformer.scene.editor

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.IntSize

@Composable
fun WorldOverlay(
    tool: Tool,
    modifier: Modifier,
    onMove: (Offset) -> Unit = {},
    onResize: (delta: Float, position: Offset, size: IntSize) -> Unit = { _, _, _ -> },
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
            onTertiary = onMove,
            onScroll = onResize,
        )
        else -> Modifier
    }

    Canvas(
        modifier = modifier then canvasModifier,
    ) {
        val start = selectionStart ?: return@Canvas
        val size = selectionSize ?: return@Canvas
        drawRect(Color(0xFF0D99FF), start, size, 0.2f)
        drawRect(Color(0xFF0D99FF), start, size, 1.0f, Stroke())
    }
}
