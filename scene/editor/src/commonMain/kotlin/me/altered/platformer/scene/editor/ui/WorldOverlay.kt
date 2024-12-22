package me.altered.platformer.scene.editor.ui

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
import me.altered.platformer.expression.InspectorInfo
import me.altered.platformer.level.data.solid
import me.altered.platformer.level.node.MutableEllipseNode
import me.altered.platformer.level.node.MutableGroupNode
import me.altered.platformer.level.node.MutableLevelNode
import me.altered.platformer.level.node.MutableObjectNode
import me.altered.platformer.level.node.MutableRectangleNode
import me.altered.platformer.level.data.MutableEllipse
import me.altered.platformer.level.data.MutableGroup
import me.altered.platformer.level.data.MutableRectangle
import me.altered.platformer.util.medianOf
import me.altered.platformer.util.round
import me.altered.platformer.scene.editor.state.SelectionState
import me.altered.platformer.scene.editor.state.ToolState
import me.altered.platformer.state.TransformState
import me.altered.platformer.state.rectScreenToWorld
import me.altered.platformer.state.rectWorldToScreen
import me.altered.platformer.state.screenToWorld
import me.altered.platformer.state.worldToScreen
import kotlin.random.Random

@Composable
fun WorldOverlay(
    level: MutableLevelNode,
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
        drawHoveredRect(selection.hovered, worldToScreen)
        drawSelectedRect(selection, rectWorldToScreen)
        drawSelectionRect(selection.rect)
    }
}

// tools

private fun Modifier.cursor(
    level: MutableLevelNode,
    toolState: ToolState,
    selection: SelectionState,
    transform: TransformState,
    screenToWorld: Offset.(Size) -> Offset,
    worldToScreen: Offset.(Size) -> Offset,
    rectScreenToWorld: Rect.(Size) -> Rect,
) = this
    .hover { position, size ->
        selection.hovered = level.findHovered(position.screenToWorld(size))
    }
    .selectionRect(
        state = selection,
        worldToScreen = worldToScreen,
        onSelect = { rect, size ->
            selection.selectAll(level.findSelected(rect.rectScreenToWorld(size)))
        },
        onDrag = { delta, size ->
            val delta = delta / transform.getScale(size)
            selection.selection.forEach { node ->
                node.obj.x.staticValue = (node.obj.x.staticValue + delta.x).round()
                node.obj.y.staticValue = (node.obj.y.staticValue + delta.y).round()
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
    level: MutableLevelNode,
    toolState: ToolState,
    selection: SelectionState,
    screenToWorld: Offset.(Size) -> Offset
) = this
    .pointerHoverIcon(PointerIcon.Crosshair)
    .createDefaultObject(screenToWorld) {
        val node = level.rectangle(it)
        level.objects += node
        selection.selectSingle(node)
        toolState.reset()
    }

private fun Modifier.ellipse(
    level: MutableLevelNode,
    toolState: ToolState,
    selection: SelectionState,
    screenToWorld: Offset.(Size) -> Offset
) = this
    .pointerHoverIcon(PointerIcon.Crosshair)
    .createDefaultObject(screenToWorld) {
        val node = level.ellipse(it)
        level.objects += node
        selection.selectSingle(node)
        toolState.reset()
    }

// level utils

fun MutableLevelNode.findHovered(position: Offset): MutableObjectNode? {
    return objects.findLast { position in it.globalBounds }
}

fun MutableLevelNode.findSelected(rect: Rect): List<MutableObjectNode> {
    return objects.filter { it.globalBounds.overlaps(rect) }
}

fun MutableLevelNode.generateUniqueId(): Long {
    var id: Long
    do {
        id = Random.nextLong()
    } while (objects.any { it.obj.id == id })
    return id
}

fun MutableLevelNode.createGroup(selection: SelectionState) {
    if (selection.selection.isEmpty()) return
    // might need to deal with -1 but ok for now
    val minIndex = selection.selection.minOf { objects.indexOf(it) }
    val x = selection.selection.medianOf { it.obj.x.staticValue }
    val y = selection.selection.medianOf { it.obj.y.staticValue }

    val node = MutableGroupNode(
        obj = MutableGroup(
            id = generateUniqueId(),
            x = AnimatedFloatState(x),
            y = AnimatedFloatState(y),
            children = selection.selection.mapTo(mutableStateListOf()) { it.obj },
        )
    )

    node.children.forEach { node ->
        node.obj.x.staticValue -= x
        node.obj.y.staticValue -= y
    }

    objects.add(minIndex, node)
    objects.removeAll(selection.selection)
    selection.selectSingle(node)
}

// drawing

private fun DrawScope.drawHoveredRect(
    obj: MutableObjectNode?,
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

private fun MutableLevelNode.rectangle(position: Offset) = MutableRectangleNode(
    MutableRectangle(
        id = generateUniqueId(),
        name = "rect",
        x = AnimatedFloatState(position.x, InspectorInfo.X),
        y = AnimatedFloatState(position.y, InspectorInfo.Y),
        fill = mutableStateListOf(
            AnimatedBrushState(solid(0xFFCCCCCC), InspectorInfo.Fill),
        ),
    )
)

private fun MutableLevelNode.ellipse(position: Offset) = MutableEllipseNode(
    MutableEllipse(
        id = generateUniqueId(),
        name = "ellipse",
        x = AnimatedFloatState(position.x, InspectorInfo.X),
        y = AnimatedFloatState(position.y, InspectorInfo.Y),
        fill = mutableStateListOf(
            AnimatedBrushState(solid(0xFFCCCCCC), InspectorInfo.Fill),
        ),
    )
)
