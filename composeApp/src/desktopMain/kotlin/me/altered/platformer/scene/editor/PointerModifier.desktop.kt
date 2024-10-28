package me.altered.platformer.scene.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isCtrlPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.IntSize

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
actual fun Modifier.pointerEvents(
    onPrimaryStart: (Offset) -> Unit,
    onPrimaryEnd: () -> Unit,
    onPrimary: (Offset) -> Unit,
    onSecondaryStart: (Offset) -> Unit,
    onSecondaryEnd: () -> Unit,
    onSecondary: (Offset) -> Unit,
    onTertiaryStart: (Offset) -> Unit,
    onTertiaryEnd: () -> Unit,
    onTertiary: (Offset) -> Unit,
    onScroll: (delta: Float, position: Offset, size: IntSize) -> Unit,
): Modifier {
    return this
        .onDrag(
            matcher = PointerMatcher.mouse(PointerButton.Primary),
            onDragStart = onPrimaryStart,
            onDragEnd = onPrimaryEnd,
            onDrag = onPrimary,
        )
        .onDrag(
            matcher = PointerMatcher.mouse(PointerButton.Secondary),
            onDragStart = onSecondaryStart,
            onDragEnd = onSecondaryEnd,
            onDrag = onSecondary,
        )
        .onDrag(
            matcher = PointerMatcher.mouse(PointerButton.Tertiary),
            onDragStart = onTertiaryStart,
            onDragEnd = onTertiaryEnd,
            onDrag = onTertiary,
        )
        .onPointerEvent(PointerEventType.Scroll) { event ->
            if (event.keyboardModifiers.isCtrlPressed) {
                val change = event.changes.first()
                onScroll(change.scrollDelta.y, change.position, size)
            }
        }
}
