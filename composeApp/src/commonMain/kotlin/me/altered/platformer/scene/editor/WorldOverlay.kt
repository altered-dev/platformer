package me.altered.platformer.scene.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.toSize
import me.altered.platformer.expression.AnimatedBrushState
import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.expression.const
import me.altered.platformer.level.data.Ellipse
import me.altered.platformer.level.data.Rectangle
import me.altered.platformer.level.data.solid
import me.altered.platformer.level.node.EllipseNode
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.level.node.RectangleNode

@Composable
fun WorldOverlay(
    toolState: ToolState,
    selectionState: SelectionState,
    worldToScreen: Offset.(size: Size) -> Offset,
    screenToWorld: Offset.(size: Size) -> Offset,
    modifier: Modifier,
    onPan: (Offset) -> Unit = {},
    onZoom: (delta: Offset, position: Offset, size: Size) -> Unit = { _, _, _ -> },
    onHover: (position: Offset, size: Size) -> ObjectNode<*>? = { _, _ -> null },
    onSelect: (rect: Rect, size: Size) -> List<ObjectNode<*>> = { _, _ -> emptyList() },
    onDrag: (delta: Offset, size: Size) -> Unit = { _, _ -> },
    placeNode: (ObjectNode<*>) -> Unit = {},
) {
    val canvasModifier = when (val tool = toolState.tool) {
        Tool.Cursor -> Modifier
            .hover { position, size -> selectionState.hovered = onHover(position, size) }
            .selectionRect(
                state = selectionState,
                worldToScreen = worldToScreen,
                onSelect = { rect, size -> selectionState.selectAll(onSelect(rect, size)) },
                onDrag = onDrag,
            )
            .pointerInput(tool) {
                detectTapGestures {
                    if (!selectionState.selectHovered()) {
                        onSelect(Rect(it, Size(1.0f, 1.0f)), size.toSize())
                    }
                }
            }
        Tool.Rectangle -> Modifier
            .pointerHoverIcon(PointerIcon.Crosshair)
            .createDefaultObject(screenToWorld) { pos ->
                val node = RectangleNode(rectangle(pos))
                placeNode(node)
                selectionState.selectSingle(node)
                toolState.reset()
            }
        Tool.Circle -> Modifier
            .pointerHoverIcon(PointerIcon.Crosshair)
            .createDefaultObject(screenToWorld) { pos ->
                val node = EllipseNode(ellipse(pos))
                placeNode(node)
                selectionState.selectSingle(node)
                toolState.reset()
            }
        else -> Modifier
    }

    Canvas(
        modifier = Modifier
            .pan(onPan, onZoom)
            .then(canvasModifier)
            .then(modifier),
    ) {
        drawHoveredRect(selectionState.hovered, worldToScreen)
        drawSelectedRect(selectionState, worldToScreen)
        drawSelectionRect(selectionState.rect)
    }
}

private fun DrawScope.drawSelectionRect(rect: Rect?) {
    if (rect == null) return
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
    state: SelectionState,
    worldToScreen: Offset.(size: Size) -> Offset,
) {
    val rect = state.getSelectionRect(size, worldToScreen) ?: return
    drawRect(Color.Blue, rect.topLeft, rect.size, style = Stroke())
}

private fun rectangle(position: Offset) = Rectangle(
    name = "rect",
    x = AnimatedFloatState(const(position.x)),
    y = AnimatedFloatState(const(position.y)),
    rotation = AnimatedFloatState(const(0.0f)),
    width = AnimatedFloatState(const(1.0f)),
    height = AnimatedFloatState(const(1.0f)),
    cornerRadius = AnimatedFloatState(const(0.0f)),
    fill = AnimatedBrushState(const(solid(0xFFFCBFB8))),
    stroke = const(solid(0x00000000)),
    strokeWidth = AnimatedFloatState(const(0.0f)),
)

private fun ellipse(position: Offset) = Ellipse(
    name = "circle",
    x = AnimatedFloatState(const(position.x)),
    y = AnimatedFloatState(const(position.y)),
    rotation = AnimatedFloatState(const(0.0f)),
    width = AnimatedFloatState(const(1.0f)),
    height = AnimatedFloatState(const(1.0f)),
    fill = AnimatedBrushState(const(solid(0xFFFCBFB8))),
    stroke = const(solid(0x00000000)),
    strokeWidth = AnimatedFloatState(const(0.0f)),
)
