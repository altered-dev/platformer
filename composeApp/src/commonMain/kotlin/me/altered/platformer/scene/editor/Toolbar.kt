package me.altered.platformer.scene.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import me.altered.platformer.ui.IconButton
import me.altered.platformer.ui.SelectorButton
import me.altered.platformer.ui.SelectorRow
import me.altered.platformer.ui.Separator
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import me.altered.platformer.Res
import me.altered.platformer.circle
import me.altered.platformer.copy
import me.altered.platformer.cursor
import me.altered.platformer.cut
import me.altered.platformer.menu
import me.altered.platformer.paste
import me.altered.platformer.pen
import me.altered.platformer.play
import me.altered.platformer.rectangle
import me.altered.platformer.redo
import me.altered.platformer.text
import me.altered.platformer.triangle
import me.altered.platformer.undo

@Composable
fun Toolbar(
    selectedTool: Tool,
    onToolSelected: (Tool) -> Unit,
    onUndoClick: () -> Unit = {},
    onRedoClick: () -> Unit = {},
    onCopyClick: () -> Unit = {},
    onCutClick: () -> Unit = {},
    onPasteClick: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SelectorRow(modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)) {
            Tool.entries.forEach { tool ->
                SelectorButton(
                    selected = tool == selectedTool,
                    onClick = { onToolSelected(tool) },
                ) {
                    Icon(painterResource(tool.icon), null)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = "my level lol",
            color = Color(0xFF999999), // TODO: to style
        )
        Spacer(modifier = Modifier.weight(1.0f))
        IconButton(onClick = onUndoClick) {
            Icon(painterResource(Res.drawable.undo), null)
        }
        IconButton(onClick = onRedoClick) {
            Icon(painterResource(Res.drawable.redo), null)
        }
        Separator()
        IconButton(onClick = onCopyClick) {
            Icon(painterResource(Res.drawable.copy), null)
        }
        IconButton(onClick = onCutClick) {
            Icon(painterResource(Res.drawable.cut), null)
        }
        IconButton(onClick = onPasteClick) {
            Icon(painterResource(Res.drawable.paste), null)
        }
        Separator()
        IconButton(onClick = onPlayClick) {
            Icon(painterResource(Res.drawable.play), null)
        }
        IconButton(onClick = onMenuClick) {
            Icon(painterResource(Res.drawable.menu), null)
        }
    }
}

// TODO: move drawable resource elsewhere
enum class Tool(val icon: DrawableResource) {
    CURSOR(Res.drawable.cursor),
    PEN(Res.drawable.pen),
    RECTANGLE(Res.drawable.rectangle),
    CIRCLE(Res.drawable.circle),
    TRIANGLE(Res.drawable.triangle),
    TEXT(Res.drawable.text),
}