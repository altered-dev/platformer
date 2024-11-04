package me.altered.platformer.scene.editor

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.toSize
import kotlin.math.max
import kotlin.math.min

fun Modifier.selectionRect(
    state: SelectionState,
    onSelect: (selection: Rect, size: Size) -> Unit,
    enabled: Boolean = true,
): Modifier = pointerInput(enabled) {
    if (!enabled) return@pointerInput
    var start: Offset = Offset.Unspecified
    var end: Offset = Offset.Unspecified
    detectDragGestures(
        onDragStart = {
            start = it
            end = it
            state.rect = Rect(it, it)
        },
        onDragEnd = {
            state.rect = null
            start = Offset.Unspecified
            end = Offset.Unspecified
        },
        onDrag = { change, delta ->
            change.consume()
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
}
