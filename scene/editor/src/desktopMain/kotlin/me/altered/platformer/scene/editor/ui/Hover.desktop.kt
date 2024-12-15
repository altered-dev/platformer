package me.altered.platformer.scene.editor.ui

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.toSize

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.hover(
    onHover: (position: Offset, size: Size) -> Unit,
): Modifier = onPointerEvent(PointerEventType.Move) {
    onHover(it.changes.first().position, size.toSize())
}
