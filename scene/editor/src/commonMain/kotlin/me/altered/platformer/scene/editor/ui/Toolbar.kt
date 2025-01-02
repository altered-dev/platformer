package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import me.altered.platformer.scene.editor.state.ToolState
import me.altered.platformer.resources.Res
import me.altered.platformer.resources.back
import me.altered.platformer.resources.circle
import me.altered.platformer.resources.copy
import me.altered.platformer.resources.cursor
import me.altered.platformer.resources.cut
import me.altered.platformer.resources.menu
import me.altered.platformer.resources.paste
import me.altered.platformer.resources.pen
import me.altered.platformer.resources.play
import me.altered.platformer.resources.rectangle
import me.altered.platformer.resources.redo
import me.altered.platformer.resources.text
import me.altered.platformer.resources.triangle
import me.altered.platformer.resources.undo
import me.altered.platformer.ui.Icon
import me.altered.platformer.ui.IconButton
import me.altered.platformer.ui.OutlinedButton
import me.altered.platformer.ui.SelectorButton
import me.altered.platformer.ui.SelectorRow
import me.altered.platformer.ui.Separator
import me.altered.platformer.ui.Text
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun Toolbar(
    levelName: String,
    toolState: ToolState,
    actionEditorState: ActionEditorState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onUndoClick: () -> Unit = {},
    onRedoClick: () -> Unit = {},
    onCopyClick: () -> Unit = {},
    onCutClick: () -> Unit = {},
    onPasteClick: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            actionEditorState.action?.let {
                OutlinedButton(
                    onClick = { actionEditorState.action = null },
                ) {
                    Icon(painterResource(Res.drawable.back))
                    Text("Back to editor")
                }
            } ?: run {
                IconButton(onClick = onBackClick) {
                    Icon(painterResource(Res.drawable.back))
                }
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
            }
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
        BasicText(
            text = levelName,
            style = TextStyle(
                color = Color(0xFF999999), // TODO: to style
            ),
        )
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
