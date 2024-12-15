package me.altered.platformer.scene.editor.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.toSize
import me.altered.platformer.ui.detectTransformGestures

actual fun Modifier.pan(
    onPan: (delta: Offset) -> Unit,
    onZoom: (delta: Offset, position: Offset, size: Size) -> Unit,
): Modifier = pointerInput(Unit) {
    detectTransformGestures { centroid, pan, zoom, _, changes ->
        if (changes.size < 2) return@detectTransformGestures
        onPan(pan)
        onZoom(Offset(zoom, 0.0f), centroid, size.toSize())
    }
}
