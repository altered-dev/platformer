package me.altered.platformer.scene.editor.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import me.altered.platformer.scene.editor.ui.Tool

@Stable
class ToolState(initial: Tool) {

    var tool by mutableStateOf(initial)

    fun reset() {
        tool = Tool.Cursor
    }
}

@Composable
fun rememberToolState(initial: Tool = Tool.Cursor) = remember { ToolState(initial) }
