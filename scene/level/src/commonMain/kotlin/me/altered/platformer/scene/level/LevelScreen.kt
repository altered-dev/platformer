package me.altered.platformer.scene.level

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import me.altered.platformer.engine.node.World
import me.altered.platformer.level.data.repository.LevelRepository
import me.altered.platformer.level.node.LevelNode
import me.altered.platformer.level.node.World
import me.altered.platformer.level.player.Player
import me.altered.platformer.resources.Res
import me.altered.platformer.resources.back
import me.altered.platformer.state.rememberTimelineState
import me.altered.platformer.state.rememberTransformState
import me.altered.platformer.state.transform
import me.altered.platformer.ui.Icon
import me.altered.platformer.ui.IconButton
import me.altered.platformer.ui.LocalContentColor
import me.altered.platformer.ui.OutlinedButton
import me.altered.platformer.ui.Text
import org.jetbrains.compose.resources.painterResource

@Serializable
data class LevelScreen(
    val name: String,
)

private sealed interface State {

    data object Loading : State

    data class Content(val level: LevelNode) : State

    data class Error(val throwable: Throwable) : State
}

@Composable
fun LevelScreen(
    name: String,
    repository: LevelRepository,
    navigateToEditor: () -> Unit = {},
    navigateBack: () -> Unit = {}
) {
    var state by remember { mutableStateOf<State>(State.Loading) }
    LaunchedEffect(name) {
        withContext(Dispatchers.IO) {
            state = repository.load(name).fold(
                onSuccess = { State.Content(it.toNode()) },
                onFailure = { State.Error(it) },
            )
        }
    }
    when (val state = state) {
        State.Loading -> LoadingScreen()
        is State.Content -> LevelScreen(
            level = state.level,
            navigateToEditor = navigateToEditor,
            navigateBack = navigateBack,
        )
        is State.Error -> ErrorScreen(
            throwable = state.throwable,
            onBackClick = navigateBack,
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
private fun LevelScreen(
    level: LevelNode,
    navigateToEditor: () -> Unit = {},
    navigateBack: () -> Unit = {}
) {
    val transform = rememberTransformState()
    val timelineState = rememberTimelineState()
    var timeDirection by remember { mutableFloatStateOf(1.0f) }
    val world = remember(level) {
        World(
            level = level,
            screenToWorld = { size -> with(transform) { screenToWorld(size) } },
            player = Player(),
        )
    }

    LaunchedEffect(level, timelineState) {
        snapshotFlow { timelineState.time }
            .collect { time -> level.eval(time) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        World(
            root = world,
            modifier = Modifier
                .fillMaxSize()
                .transform(transform)
                .onKeyEvent { event ->
                    when (event.type) {
                        KeyEventType.KeyDown -> when (event.key) {
                            Key.DirectionRight -> { timeDirection = 1.0f; true }
                            Key.DirectionLeft -> { timeDirection = -1.0f; true }
                            Key.E -> { navigateToEditor(); true }
                            Key.T -> { world.player?.position = Offset.Zero; true }
                            else -> false
                        }
                        KeyEventType.KeyUp -> when (event.key) {
                            Key.DirectionRight -> { timeDirection = 0.0f; true }
                            Key.DirectionLeft -> { timeDirection = 0.0f; true }
                            else -> false
                        }
                        else -> false
                    }
                },
            onUpdate = { delta -> timelineState.time = (timelineState.time + timeDirection * delta).coerceAtLeast(0.0f) }
        )
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            IconButton(onClick = navigateBack, tint = Color(0xFF262626)) {
                Icon(painterResource(Res.drawable.back))
            }
            BasicText("time: ${timelineState.roundedTime}")
        }
        ControlButton(
            onDown = { world.player?.leftDown() },
            onUp = { world.player?.dirUp() },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
        ) {
            Icon(painterResource(Res.drawable.back))
        }
        ControlButton(
            onDown = { world.player?.rightDown() },
            onUp = { world.player?.dirUp() },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .rotate(180.0f) // no forward icon yet
                .padding(top = 16.dp, end = 80.dp),
        ) {
            Icon(painterResource(Res.drawable.back))
        }
        ControlButton(
            onDown = { world.player?.jumpDown() },
            onUp = { world.player?.jumpUp() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .rotate(90.0f) // no up icon yet
                .padding(16.dp),
        ) {
            Icon(painterResource(Res.drawable.back))
        }
    }
}

@Composable
private fun ControlButton(
    onDown: () -> Unit,
    onUp: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF333333))
            .padding(horizontal = 16.dp)
            .pointerInput(onDown, onUp) {
                awaitEachGesture {
                    val down = awaitFirstDown().also { it.consume() }
                    onDown()
                    val up = waitForUpOrCancellation()
                        ?.also { it.consume() }
                    onUp()
                }
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color(0xFFCCCCCC),
        ) {
            content()
        }
    }
}