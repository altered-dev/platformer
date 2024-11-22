package me.altered.platformer.scene.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import me.altered.platformer.expression.AnimatedBrushState
import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.level.data.solid
import me.altered.platformer.level.objects.MutableEllipse
import me.altered.platformer.level.objects.MutableLevel
import me.altered.platformer.level.objects.MutableObject
import me.altered.platformer.level.objects.MutableRectangle

@Composable
fun WorldOverlay(
    level: MutableLevel,
    toolState: ToolState,
    selection: SelectionState,
    transform: TransformState,
    modifier: Modifier,
) {
    val screenToWorld = remember(transform) { transform.screenToWorld }
    val worldToScreen = remember(transform) { transform.worldToScreen }
    val rectScreenToWorld = remember(transform) { transform.rectScreenToWorld }
    val rectWorldToScreen = remember(transform) { transform.rectWorldToScreen }

    val canvasModifier = when (val tool = toolState.tool) {
        Tool.Cursor -> Modifier.cursor(
            level = level,
            toolState = toolState,
            selection = selection,
            transform = transform,
            screenToWorld = screenToWorld,
            worldToScreen = worldToScreen,
            rectScreenToWorld = rectScreenToWorld,
        )
        Tool.Rectangle -> Modifier.rectangle(
            level = level,
            toolState = toolState,
            selection = selection,
            screenToWorld = screenToWorld,
        )
        Tool.Circle -> Modifier.ellipse(
            level = level,
            toolState = toolState,
            selection = selection,
            screenToWorld = screenToWorld,
        )
        else -> Modifier
    }

    Canvas(
        modifier = modifier
            .then(canvasModifier),
    ) {
        drawHoveredRect(selection.hovered2, worldToScreen)
        drawSelectedRect(selection, rectWorldToScreen)
        drawSelectionRect(selection.rect)
    }
}

// tools

private fun Modifier.cursor(
    level: MutableLevel,
    toolState: ToolState,
    selection: SelectionState,
    transform: TransformState,
    screenToWorld: Offset.(Size) -> Offset,
    worldToScreen: Offset.(Size) -> Offset,
    rectScreenToWorld: Rect.(Size) -> Rect,
) = this
    .hover { position, size ->
        selection.hovered2 = level.findHovered(position.screenToWorld(size))
    }
    .selectionRect(
        state = selection,
        worldToScreen = worldToScreen,
        onSelect = { rect, size ->
            selection.selectAll(level.findSelected(rect.rectScreenToWorld(size)))
        },
        onDrag = { delta, size ->
            val delta = delta / transform.getScale(size)
            selection.selection2.forEach { obj ->
                obj.x.staticValue = (obj.x.staticValue + delta.x).round()
                obj.y.staticValue = (obj.y.staticValue + delta.y).round()
            }
        }
    )
    .pointerInput(toolState.tool) {
        detectTapGestures {
            if (!selection.selectHovered()) {
                selection.selectSingle(level.findHovered(it))
            }
        }
    }

private fun Modifier.rectangle(
    level: MutableLevel,
    toolState: ToolState,
    selection: SelectionState,
    screenToWorld: Offset.(Size) -> Offset
) = this
    .pointerHoverIcon(PointerIcon.Crosshair)
    .createDefaultObject(screenToWorld) {
        val obj = rectangle(it)
        level.objects += obj
        selection.selectSingle(obj)
        toolState.reset()
    }

private fun Modifier.ellipse(
    level: MutableLevel,
    toolState: ToolState,
    selection: SelectionState,
    screenToWorld: Offset.(Size) -> Offset
) = this
    .pointerHoverIcon(PointerIcon.Crosshair)
    .createDefaultObject(screenToWorld) {
        val obj = ellipse(it)
        level.objects += obj
        selection.selectSingle(obj)
        toolState.reset()
    }

// level utils

fun MutableLevel.findHovered(position: Offset): MutableObject? {
    return objects.findLast { obj -> position in obj.globalBounds }
}

fun MutableLevel.findSelected(rect: Rect): List<MutableObject> {
    return objects.filter { it.globalBounds.overlaps(rect) }
}

// drawing

private fun DrawScope.drawHoveredRect(
    obj: MutableObject?,
    worldToScreen: Offset.(Size) -> Offset,
) {
    if (obj == null) return
    val bounds = obj.globalBounds
    val topLeft = bounds.topLeft.worldToScreen(size)
    val bottomRight = bounds.bottomRight.worldToScreen(size)
    val size = Size(bottomRight.x - topLeft.x, bottomRight.y - topLeft.y)
    drawRect(Color(0xFF0D99FF), topLeft, size, style = Stroke())
}

private fun DrawScope.drawSelectionRect(rect: Rect?) {
    if (rect == null) return
    drawRect(Color(0xFF0D99FF), rect.topLeft, rect.size, 0.2f)
    drawRect(Color(0xFF0D99FF), rect.topLeft, rect.size, 1.0f, Stroke(2.0f))
}

private fun DrawScope.drawSelectedRect(
    state: SelectionState,
    worldToScreen: Rect.(size: Size) -> Rect,
) {
    val rect = state.getSelectionRect()?.worldToScreen(size) ?: return
    drawRect(Color.Blue, rect.topLeft, rect.size, style = Stroke())
}

// object creation

private fun rectangle(position: Offset) = MutableRectangle(
    id = idCounter++,
    name = "rect",
    x = AnimatedFloatState(position.x),
    y = AnimatedFloatState(position.y),
    fill = mutableStateListOf(
        AnimatedBrushState(solid(0xFFFCBFB8)),
    ),
)

private fun ellipse(position: Offset) = MutableEllipse(
    id = idCounter++,
    name = "ellipse",
    x = AnimatedFloatState(position.x),
    y = AnimatedFloatState(position.y),
    fill = mutableStateListOf(
        AnimatedBrushState(solid(0xFFFCBFB8)),
    ),
)

// TODO: THIS IS VERY TEMPORARY (i hope at least)
private var idCounter = 0L
