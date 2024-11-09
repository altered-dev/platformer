package me.altered.platformer.scene.editor

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

expect fun Modifier.pan(
    onPan: (delta: Offset) -> Unit = {},
    onZoom: (delta: Offset, position: Offset, size: Size) -> Unit = { _, _, _ -> },
): Modifier
