package me.altered.platformer.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.graphicsLayer

class TransformState(
    zoom: Float,
    offset: Offset,
) {

    var zoom by mutableFloatStateOf(zoom)
    var offset by mutableStateOf(offset)
}

@Composable
fun rememberTransformState(
    zoom: Float = 1.0f,
    offset: Offset = Offset.Zero,
) = remember { TransformState(zoom, offset) }

fun Modifier.transform(
    state: TransformState
) = graphicsLayer {
    val center = size.center
    translationX = state.offset.x + center.x * state.zoom
    translationY = state.offset.y + center.y * state.zoom
    scaleX = state.zoom
    scaleY = state.zoom
}
