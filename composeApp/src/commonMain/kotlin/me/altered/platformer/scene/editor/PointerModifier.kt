package me.altered.platformer.scene.editor

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

expect fun Modifier.pointerEvents(
    onPrimaryStart: (Offset) -> Unit = {},
    onPrimaryEnd: () -> Unit = {},
    onPrimary: (Offset) -> Unit = {},
    onSecondaryStart: (Offset) -> Unit = {},
    onSecondaryEnd: () -> Unit = {},
    onSecondary: (Offset) -> Unit = {},
    onTertiaryStart: (Offset) -> Unit = {},
    onTertiaryEnd: () -> Unit = {},
    onTertiary: (Offset) -> Unit = {},
    onScroll: (delta: Offset, position: Offset, size: IntSize) -> Unit = { _, _, _ -> },
    onCtrlScroll: (delta: Offset, position: Offset, size: IntSize) -> Unit = { _, _, _ -> },
    onMove: (position: Offset, size: IntSize) -> Unit = { _, _ -> },
): Modifier
