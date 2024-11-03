package me.altered.platformer.scene.editor

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

actual fun Modifier.hover(
    onHover: (position: Offset, size: Size) -> Unit,
): Modifier = Modifier
