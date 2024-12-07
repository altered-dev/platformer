package me.altered.platformer.scene.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import me.altered.platformer.engine.node.World
import me.altered.platformer.level.Level
import me.altered.platformer.level.World
import me.altered.platformer.level.player.Player
import me.altered.platformer.level.readFromFile
import me.altered.platformer.scene.editor.state.rememberTimelineState
import me.altered.platformer.scene.editor.state.rememberTransformState
import me.altered.platformer.scene.editor.state.transform

@Serializable
data class LevelScreen(
    val name: String,
)

@Composable
fun LevelScreen(
    name: String,
    navigateToEditor: () -> Unit = {},
) {
    var level by remember { mutableStateOf<Level?>(null) }
    LaunchedEffect(name) {
        withContext(Dispatchers.IO) {
            level = Level.readFromFile(name)
        }
    }
    level?.let { level ->
        LevelScreen(
            level = level,
            navigateToEditor = navigateToEditor,
        )
    }
}

@Composable
private fun LevelScreen(
    level: Level,
    navigateToEditor: () -> Unit = {},
) {
    val transform = rememberTransformState()
    val timelineState = rememberTimelineState()
    var timeDirection by remember { mutableFloatStateOf(0.0f) }
    val world = remember(level) {
        World(
            level = level,
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
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            BasicText("time: ${timelineState.roundedTime}")
        }
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
    }
}
