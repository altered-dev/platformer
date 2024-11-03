package me.altered.platformer.scene.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size

@OptIn(ExperimentalFoundationApi::class)
actual fun Modifier.selectionRect(
    state: SelectionRectState,
    enabled: Boolean,
): Modifier = onDrag(
    enabled = enabled,
    onDragStart = { state.rect = Rect(it, Size.Zero) },
    onDragEnd = { state.rect = null },
    onDrag = { delta ->
        state.rect = state.rect?.let { rect ->
            Rect(rect.topLeft, rect.bottomRight + delta)
        }
    }
)
