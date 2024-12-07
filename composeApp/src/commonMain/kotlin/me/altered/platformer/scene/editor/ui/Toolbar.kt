package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
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
import me.altered.platformer.scene.editor.state.ToolState
import me.altered.platformer.text
import me.altered.platformer.triangle
import me.altered.platformer.ui.Icon
import me.altered.platformer.ui.IconButton
import me.altered.platformer.ui.SelectorButton
import me.altered.platformer.ui.SelectorRow
import me.altered.platformer.ui.Separator
import me.altered.platformer.undo
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun Toolbar(
    levelName: String,
    toolState: ToolState,
    modifier: Modifier = Modifier,
    onUndoClick: () -> Unit = {},
    onRedoClick: () -> Unit = {},
    onCopyClick: () -> Unit = {},
    onCutClick: () -> Unit = {},
    onPasteClick: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SelectorRow {
            Tool.entries.forEach { tool ->
                SelectorButton(
                    selected = tool == toolState.tool,
                    onClick = { toolState.tool = tool },
                ) {
                    Icon(painterResource(tool.icon))
                }
            }
        }
        Spacer(modifier = Modifier.weight(1.0f))
        BasicText(
            text = levelName,
            style = TextStyle(
                color = Color(0xFF999999), // TODO: to style
            ),
        )
        Spacer(modifier = Modifier.weight(1.0f))
        IconButton(onClick = onUndoClick) {
            Icon(painterResource(Res.drawable.undo))
        }
        IconButton(onClick = onRedoClick) {
            Icon(painterResource(Res.drawable.redo))
        }
        Separator()
        IconButton(onClick = onCopyClick) {
            Icon(painterResource(Res.drawable.copy))
        }
        IconButton(onClick = onCutClick) {
            Icon(painterResource(Res.drawable.cut))
        }
        IconButton(onClick = onPasteClick) {
            Icon(painterResource(Res.drawable.paste))
        }
        Separator()
        IconButton(onClick = onPlayClick) {
            Icon(painterResource(Res.drawable.play))
        }
        IconButton(onClick = onMenuClick) {
            Icon(painterResource(Res.drawable.menu))
        }
    }
}

// TODO: move drawable resource elsewhere
@Immutable
enum class Tool(val icon: DrawableResource) {
    Cursor(Res.drawable.cursor),
    Pen(Res.drawable.pen),
    Rectangle(Res.drawable.rectangle),
    Circle(Res.drawable.circle),
    Triangle(Res.drawable.triangle),
    Text(Res.drawable.text),
}
