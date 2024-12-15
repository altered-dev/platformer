package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isCtrlPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.toSize

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
actual fun Modifier.pan(
    onPan: (delta: Offset) -> Unit,
    onZoom: (delta: Offset, position: Offset, size: Size) -> Unit,
): Modifier = onPointerEvent(PointerEventType.Scroll) {
    if (it.keyboardModifiers.isCtrlPressed) {
        val change = it.changes.first()
        onZoom(change.scrollDelta, change.position, size.toSize())
    } else {
        val change = it.changes.first()
        onPan(change.scrollDelta)
    }
}.onDrag(
    matcher = PointerMatcher.mouse(PointerButton.Tertiary),
    onDrag = onPan,
)
