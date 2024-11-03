package me.altered.platformer.scene.editor

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput

actual fun Modifier.selectionRect(
    state: SelectionRectState,
    enabled: Boolean,
): Modifier = pointerInput(Unit) {
    if (enabled) {
        detectDragGestures(
            onDragStart = { cursorPos ->
                state.rect = Rect(cursorPos, Size.Zero)
                println("start ${state.rect}")
            },
            onDragEnd = {
                println("end ${state.rect}")
                state.rect = null
            },
            onDrag = { _, delta ->
                state.rect = state.rect?.let { rect ->
                    Rect(rect.topLeft, rect.bottomRight + delta)
                }
                println("drag ${state.rect}")
            },
        )
    }
}
