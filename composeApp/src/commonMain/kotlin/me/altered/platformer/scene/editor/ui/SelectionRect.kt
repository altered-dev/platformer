package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.CancellationException
import me.altered.platformer.scene.editor.state.SelectionState
import kotlin.math.max
import kotlin.math.min

fun Modifier.selectionRect(
    state: SelectionState,
    worldToScreen: Offset.(Size) -> Offset,
    onSelect: (selection: Rect, size: Size) -> Unit,
    onDrag: (delta: Offset, size: Size) -> Unit,
    enabled: Boolean = true,
): Modifier = pointerInput(enabled) {
    if (!enabled) return@pointerInput
    var start: Offset = Offset.Unspecified
    var end: Offset = Offset.Unspecified
    var isDragging = false
    try {
        detectDragGestures(
            onDragStart = { pos ->
                var rect = state.getSelectionRect(size.toSize(), worldToScreen)
                isDragging = rect?.contains(pos) == true
                if (isDragging) return@detectDragGestures
                state.selectHovered()
                rect = state.getSelectionRect(size.toSize(), worldToScreen)
                isDragging = rect?.contains(pos) == true
                if (isDragging) return@detectDragGestures
                start = pos
                end = pos
                state.rect = Rect(pos, pos)
            },
            onDragEnd = {
                isDragging = false
                state.rect = null
                start = Offset.Unspecified
                end = Offset.Unspecified
            },
            onDrag = { change, delta ->
                change.consume()
                if (isDragging) {
                    return@detectDragGestures onDrag(delta, size.toSize())
                }
                end += delta
                val newRect = Rect(
                    left = min(start.x, end.x),
                    top = min(start.y, end.y),
                    right = max(start.x, end.x),
                    bottom = max(start.y, end.y),
                )
                state.rect = newRect
                onSelect(newRect, size.toSize())
            }
        )
    } catch (e: CancellationException) {
        state.rect = null
    }
}
