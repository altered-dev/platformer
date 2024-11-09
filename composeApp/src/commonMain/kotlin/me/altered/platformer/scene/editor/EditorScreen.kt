package me.altered.platformer.scene.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import me.altered.platformer.engine.node.World
import me.altered.platformer.engine.ui.onDistinctKeyEvent

@Serializable
data object EditorScreen

@Composable
fun EditorScreen(
    level: MutableLevelState = rememberMutableLevelState("My level"),
    onBackClick: () -> Unit = {},
) {
    val toolState = rememberToolState()
    val selectionState = rememberSelectionState()
    val timelineState = rememberTimelineState()
    val scene = remember(level) { EditorScene(level) }

    LaunchedEffect(level) {
        snapshotFlow { timelineState.time }
            .collect { scene.setTime(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF333333))
            .onDistinctKeyEvent { event ->
                when (event.key) {
                    Key.One -> toolState.tool = Tool.Cursor
                    Key.Two -> toolState.tool = Tool.Pen
                    Key.Three -> toolState.tool = Tool.Rectangle
                    Key.Four -> toolState.tool = Tool.Circle
                    Key.Five -> toolState.tool = Tool.Triangle
                    Key.Six -> toolState.tool = Tool.Text
                    Key.Delete, Key.Backspace -> {
                        scene.removeAll(selectionState.selection)
                        selectionState.deselect()
                    }
                    Key.Escape -> onBackClick()
                    else -> return@onDistinctKeyEvent false
                }
                true
            },
    ) {
        Toolbar(
            levelName = level.name,
            toolState = toolState,
        )
        Row {
            NodeTree(
                objects = level.objects,
                state = selectionState,
            )
            Column(
                modifier = Modifier.weight(1.0f),
            ) {
                Box(
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFF262626), RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF576647)),
                ) {
                    World(
                        root = scene,
                        modifier = Modifier.fillMaxSize(),
                    )
                    WorldOverlay(
                        toolState = toolState,
                        selectionState = selectionState,
                        worldToScreen = { scene.worldToScreen(this, it) },
                        screenToWorld = { scene.screenToWorld(this, it) },
                        modifier = Modifier.fillMaxSize(),
                        onPan = { scene.pan(it) },
                        onZoom = { delta, position, size -> scene.zoom(delta, position, size) },
                        onHover = { position, size -> scene.hover(position, size) },
                        onSelect = { rect, size -> scene.select(rect, size) },
                        onDrag = { delta, size -> scene.drag(selectionState.selection, delta, size) },
                        placeNode = { scene.place(it) }
                    )
                }
                Timeline(
                    state = timelineState,
                    selectionState = selectionState,
                )
            }
            Inspector(
                timelineState = timelineState,
                objects = selectionState.selection,
            )
        }
    }
}
