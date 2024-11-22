package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.unit.toSize

fun Modifier.createDefaultObject(
    screenToWorld: Offset.(size: Size) -> Offset,
    onCreate: (position: Offset) -> Unit,
): Modifier = pointerInput(onCreate) {
    detectTapGestures {
        val pos = it.screenToWorld(size.toSize())
            .plus(Offset(0.5f, 0.5f))
            .round()
            .toOffset()
            .minus(Offset(0.5f, 0.5f))
        onCreate(pos)
    }
}
