package me.altered.platformer.scene.editor.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import me.altered.platformer.state.TransformState
import me.altered.platformer.state.WorldTransformState

expect fun Modifier.pan(
    onPan: (delta: Offset) -> Unit = {},
    onZoom: (delta: Offset, position: Offset, size: Size) -> Unit = { _, _, _ -> },
): Modifier

fun Modifier.pan(
    state: TransformState,
) = pan(
    onPan = { state.offset += it },
    onZoom = { delta, position, size ->
        if (delta == Offset.Zero) return@pan
        val cursorPos = position - (state.offset + size.center)
        val zoomFrom = state.zoom
        when {
            delta.x != 0.0f -> state.zoom *= delta.x
            delta.y > 0.0f -> state.zoom /= 1.0f + delta.y * 0.1f
            delta.y < 0.0f -> state.zoom *= 1.0f - delta.y * 0.1f
        }
        state.offset += cursorPos * (1 - state.zoom / zoomFrom)
    },
)

fun Modifier.pan(
    state: WorldTransformState,
) = pan(
    onPan = { state.offset += it },
    onZoom = { delta, position, size ->
        if (delta == Offset.Zero) return@pan
        val cursorPos = position - (state.offset + size.center)
        val zoomFrom = state.zoom
        when {
            delta.x != 0.0f -> state.zoom *= delta.x
            delta.y > 0.0f -> state.zoom /= 1.0f + delta.y * 0.1f
            delta.y < 0.0f -> state.zoom *= 1.0f - delta.y * 0.1f
        }
        state.offset += cursorPos * (1 - state.zoom / zoomFrom)
    },
)
