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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import kotlinx.serialization.Serializable
import me.altered.platformer.node.World

@Serializable
data object EditorScreen

@Composable
fun EditorScreen(
    onBackClick: () -> Unit = {},
) {
    val scene = remember { EditorScene() }
    val (tool, setTool) = remember { mutableStateOf(Tool.Cursor) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF333333))
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown && event.key == Key.Escape) {
                    onBackClick()
                    true
                } else false
            },
    ) {
        Toolbar(
            selectedTool = tool,
            onToolSelected = setTool,
        )
        Row {
            NodeTree()
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
                        tool = tool,
                        modifier = Modifier.fillMaxSize(),
                        onMove = { scene.move(it) },
                        onResize = { delta, position, size ->  scene.resize(delta, position, size.toSize()) },
                    )
                }
                Timeline()
            }
            Inspector()
        }
    }
}
