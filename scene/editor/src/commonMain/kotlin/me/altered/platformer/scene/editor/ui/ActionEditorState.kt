package me.altered.platformer.scene.editor.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import me.altered.platformer.action.MutableAction

class ActionEditorState {

    var action by mutableStateOf<MutableAction?>(null)
}

@Composable
fun rememberActionEditorState() = remember { ActionEditorState() }