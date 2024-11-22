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
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import me.altered.platformer.engine.node.World
import me.altered.platformer.level.World2
import me.altered.platformer.level.objects.MutableLevel
import me.altered.platformer.level.objects.MutableLevelImpl

@Serializable
data object EditorScreen

const val WorldScale = 0.05f

@Composable
fun EditorScreen2(
    level: MutableLevel = remember { MutableLevelImpl("My level") },
    onBackClick: () -> Unit = {},
) {
    val transform = rememberTransformState()
    val toolState = rememberToolState()
    val selectionState = rememberSelectionState()
    val timelineState = rememberTimelineState()
    val world = remember(level) {
        World2(
            level = level,
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
    ) {
        Toolbar(
            levelName = level.name,
            toolState = toolState,
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
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF576647)),
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
