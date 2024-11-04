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
import kotlinx.serialization.Serializable
import me.altered.platformer.engine.node.World
import me.altered.platformer.expression.const
import me.altered.platformer.level.data.Ellipse
import me.altered.platformer.level.data.Rectangle
import me.altered.platformer.level.data.solid
import me.altered.platformer.level.node.EllipseNode
import me.altered.platformer.level.node.RectangleNode

@Serializable
data object EditorScreen

@Composable
fun EditorScreen(
    level: MutableLevelState = rememberMutableLevelState(
        name = "My level",
        listOf(
            RectangleNode(
                obj = Rectangle(
                    name = "Rectangle",
                    x = const(0.0f),
                    y = const(0.0f),
                    rotation = const(0.0f),
                    width = const(1.0f),
                    height = const(1.0f),
                    cornerRadius = const(0.0f),
                    fill = const(solid(0xFFFCBFB8)),
                    stroke = const(solid(0x00000000)),
                    strokeWidth = const(0.0f),
                ),
            ),
            EllipseNode(
                obj = Ellipse(
                    name = "Ellipse",
                    x = const(5.0f),
                    y = const(3.0f),
                    rotation = const(0.0f),
                    width = const(1.0f),
                    height = const(1.0f),
                    fill = const(solid(0xFFFCBFB8)),
                    stroke = const(solid(0x00000000)),
                    strokeWidth = const(0.0f),
                ),
            ),
            EllipseNode(
                obj = Ellipse(
                    name = "Ellipse",
                    x = const(-8.0f),
                    y = const(5.0f),
                    rotation = const(0.0f),
                    width = const(5.0f),
                    height = const(3.5f),
                    fill = const(solid(0xFFFCBFB8)),
                    stroke = const(solid(0x00000000)),
                    strokeWidth = const(0.0f),
                ),
            ),
        )
    ),
    onBackClick: () -> Unit = {},
) {
    val scene = remember(level) { EditorScene(level) }
    val toolState = rememberToolState()
    val selectionState = rememberSelectionState()

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
                        placeNode = { scene.place(it) }
                    )
                }
                Timeline()
            }
            Inspector(
                objects = selectionState.selection,
            )
        }
    }
}
