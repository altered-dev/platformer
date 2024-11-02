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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import kotlinx.serialization.Serializable
import me.altered.platformer.expression.const
import me.altered.platformer.level.data.Ellipse
import me.altered.platformer.level.data.Rectangle
import me.altered.platformer.level.data.emptyBrush
import me.altered.platformer.level.data.solid
import me.altered.platformer.level.node.EllipseNode
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.level.node.RectangleNode
import me.altered.platformer.node.World

@Serializable
data object EditorScreen

@Composable
fun EditorScreen(
    onBackClick: () -> Unit = {},
) {
    val scene = remember {
        EditorScene(
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
                    stroke = const(emptyBrush()),
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
                    stroke = const(emptyBrush()),
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
                    stroke = const(emptyBrush()),
                    strokeWidth = const(0.0f),
                ),
            ),
        )
    }
    val (tool, setTool) = remember { mutableStateOf(Tool.Cursor) }
    var hovered by remember { mutableStateOf<ObjectNode<*>?>(null) }
    val selected = remember { mutableStateListOf<Pair<ObjectNode<*>, Rect>>() }
    var bounds by remember { mutableStateOf<Rect?>(null) }

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
                        hoveredNode = hovered,
                        hoveredBounds = bounds,
                        selected = selected,
                        modifier = Modifier.fillMaxSize(),
                        onDrag = { scene.move(it) },
                        onResize = { delta, position, size -> scene.resize(delta, position, size.toSize()) },
                        onHover = { position, size ->
                            val (h, b) = scene.hover(position, size.toSize()) ?: (null to null)
                            hovered = h
                            bounds = b
                        },
                        onSelect = {
                            println("selecting $hovered")
                            selected.clear()
                            hovered?.let { selected += (it to bounds!!) }
                        }
                    )
                }
                Timeline()
            }
            Inspector(
                objects = selected,
            )
        }
    }
}
