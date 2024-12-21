package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import me.altered.platformer.engine.node.World
import me.altered.platformer.engine.ui.onDistinctKeyEvent
import me.altered.platformer.level.data.repository.LevelRepository
import me.altered.platformer.level.node.Grid
import me.altered.platformer.level.node.MutableLevelNode
import me.altered.platformer.level.node.World
import me.altered.platformer.scene.editor.state.rememberSelectionState
import me.altered.platformer.scene.editor.state.rememberToolState
import me.altered.platformer.state.rememberTimelineState
import me.altered.platformer.state.rememberTransformState
import me.altered.platformer.state.transform
import me.altered.platformer.ui.OutlinedButton
import me.altered.platformer.ui.Text

@Serializable
data class EditorScreen(
    val name: String,
)

private sealed interface State {

    data object Loading : State

    data class Content(val level: MutableLevelNode) : State

    data class Error(val throwable: Throwable) : State
}

@Composable
fun EditorScreen(
    name: String,
    repository: LevelRepository,
    onBackClick: () -> Unit,
    onPlayClick: () -> Unit,
) {
    var state by remember { mutableStateOf<State>(State.Loading) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(name) {
        state = repository.load(name).fold(
            onSuccess = { State.Content(it.toMutableNode()) },
            onFailure = { State.Error(it) },
        )
    }
    when (val state = state) {
        State.Loading -> LoadingScreen()
        is State.Content -> EditorScreen(
            level = state.level,
            onBackClick = onBackClick,
            onPlayClick = {
                coroutineScope.launch {
                    repository.save(state.level.toLevel())
                    onPlayClick()
                }
            },
        )
        is State.Error -> ErrorScreen(
            throwable = state.throwable,
            onBackClick = onBackClick,
        )
    }
}

// TODO: to common
@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text("Loading")
    }
}

@Composable
private fun ErrorScreen(
    throwable: Throwable,
    onBackClick: () -> Unit,
) {
    LaunchedEffect(throwable) {
        throwable.printStackTrace()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Error while loading level:\n$throwable")
        OutlinedButton(
            onClick = onBackClick,
        ) {
            Text("Back")
        }
    }
}

@Composable
private fun EditorScreen(
    level: MutableLevelNode,
    onBackClick: () -> Unit = {},
    onPlayClick: () -> Unit = {},
) {
    val transform = rememberTransformState()
    val toolState = rememberToolState()
    val selectionState = rememberSelectionState()
    val timelineState = rememberTimelineState()
    val world = remember(level) {
        World(
            level = level,
            screenToWorld = { size -> with(transform) { screenToWorld(size) } },
            grid = Grid(
                screenToWorld = { size -> with(transform) { screenToWorld(size) } },
            ),
        )
    }

    LaunchedEffect(level, timelineState) {
        snapshotFlow { timelineState.time }
            .collect { time -> level.eval(time) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF333333))
            .onDistinctKeyEvent { event ->
                when {
                    event.type == KeyEventType.KeyDown && event.key == Key.G && event.isCtrlPressed -> {
                        level.createGroup(selectionState)
                        true
                    }
                    event.type == KeyEventType.KeyDown && event.key == Key.Delete -> {
                        level.objects.removeAll(selectionState.selection)
                        selectionState.deselect()
                        true
                    }
                    else -> false
                }
            }
    ) {
        Toolbar(
            levelName = level.name,
            toolState = toolState,
            onBackClick = onBackClick,
            onPlayClick = onPlayClick,
        )
        Row {
            NodeTree(
                level = level,
                selectionState = selectionState,
            )
            Column(
                modifier = Modifier.weight(1.0f),
            ) {
                Box(
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFF262626), RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)),
                ) {
                    World(
                        root = world,
                        modifier = Modifier
                            .fillMaxSize()
                            .transform(transform),
                    )
                    WorldOverlay(
                        level = level,
                        toolState = toolState,
                        selection = selectionState,
                        transform = transform,
                        modifier = Modifier
                            .fillMaxSize()
                            .pan(transform),
                    )
                }
                Timeline(
                    state = timelineState,
                    selectionState = selectionState,
                )
            }
            Inspector(
                selectionState = selectionState,
                timelineState = timelineState,
            )
        }
    }
}
