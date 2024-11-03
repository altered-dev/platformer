package me.altered.platformer.scene.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect

expect fun Modifier.selectionRect(
    state: SelectionRectState,
    enabled: Boolean = true,
): Modifier

class SelectionRectState(
    initial: Rect? = null,
) {

    var rect by mutableStateOf(initial)
}

@Composable
fun rememberSelectionRectState(initial: Rect? = null) =
    remember { SelectionRectState(initial) }
