package me.altered.platformer.scene.editor

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput

actual fun Modifier.pan(
    onPan: (delta: Offset) -> Unit,
    onZoom: (delta: Offset, position: Offset, size: Size) -> Unit,
): Modifier = pointerInput(Unit) {
    detectTransformGestures { _, pan, _, _ ->
        onPan(pan)
    }
}
