package me.altered.platformer.scene.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import me.altered.platformer.level.node.ObjectNode
import kotlin.math.max
import kotlin.math.min

@Composable
fun WorldOverlay(
    tool: Tool,
    hoveredNode: ObjectNode<*>?,
    selected: List<ObjectNode<*>>,
    worldToScreen: Offset.(size: Size) -> Offset,
    screenToWorld: Offset.(size: Size) -> Offset,
    modifier: Modifier,
    onPan: (Offset) -> Unit = {},
    onZoom: (delta: Offset, position: Offset, size: Size) -> Unit = { _, _, _ -> },
    onHover: (position: Offset, size: Size) -> Unit = { _, _ -> },
    onSelect: () -> Unit = {},
) {
    val selectionRectState = rememberSelectionRectState()

    val canvasModifier = when (tool) {
        Tool.Cursor -> Modifier
            .pan(onPan, onZoom)
            .hover(onHover)
            .selectionRect(selectionRectState)
            .pointerInput(Unit) {
                detectTapGestures { onSelect() }
            }
        else -> Modifier
            .pan(onPan, onZoom)
    }
    Canvas(
        modifier = modifier then canvasModifier,
    ) {
        drawHoveredRect(hoveredNode, worldToScreen)
        drawSelectedRect(selected, worldToScreen)
        drawSelectionRect(selectionRectState)
    }
}

private fun DrawScope.drawSelectionRect(
    state: SelectionRectState,
) {
    val rect = state.rect ?: return
    drawRect(Color(0xFF0D99FF), rect.topLeft, rect.size, 0.2f)
    drawRect(Color(0xFF0D99FF), rect.topLeft, rect.size, 1.0f, Stroke(2.0f))
}

private fun DrawScope.drawHoveredRect(
    node: ObjectNode<*>?,
    worldToScreen: Offset.(size: Size) -> Offset,
) {
    if (node == null) return
    val bounds = node.globalBounds
    val topLeft = bounds.topLeft.worldToScreen(size)
    val bottomRight = bounds.bottomRight.worldToScreen(size)
    val size = Size(bottomRight.x - topLeft.x, bottomRight.y - topLeft.y)
    drawRect(Color(0xFF0D99FF), topLeft, size, style = Stroke())
}

private fun DrawScope.drawSelectedRect(
    nodes: List<ObjectNode<*>>,
    worldToScreen: Offset.(size: Size) -> Offset,
) {
    if (nodes.isEmpty()) return
    var left = Float.NEGATIVE_INFINITY
    var top = Float.NEGATIVE_INFINITY
    var right = Float.POSITIVE_INFINITY
    var bottom = Float.POSITIVE_INFINITY
    nodes.forEach { node ->
        val bounds = node.globalBounds
        left = max(left, bounds.left)
        top = max(top, bounds.top)
        right = min(right, bounds.right)
        bottom = min(bottom, bounds.bottom)
    }

    val topLeft = Offset(left, top).worldToScreen(size)
    val bottomRight = Offset(right, bottom).worldToScreen(size)
    val size = Size(bottomRight.x - topLeft.x, bottomRight.y - topLeft.y)

    drawRect(Color.Blue, topLeft, size, style = Stroke())
}
