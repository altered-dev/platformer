package me.altered.platformer.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.graphicsLayer
import me.altered.platformer.engine.geometry.scale

private const val WorldScale = 0.05f

class WorldTransformState(
    zoom: Float,
    offset: Offset,
) {

    var zoom by mutableFloatStateOf(zoom)
    var offset by mutableStateOf(offset)

    fun getOffset(size: Size) = offset + size.center

    fun getScale(size: Size) = zoom * size.height * WorldScale

    fun screenToWorld(offset: Offset, size: Size) = (offset - getOffset(size)) / getScale(size)

    fun worldToScreen(offset: Offset, size: Size) = offset * getScale(size) + getOffset(size)

    fun screenToWorld(rect: Rect, size: Size) = rect.translate(-getOffset(size)).scale(1.0f / getScale(size))

    fun worldToScreen(rect: Rect, size: Size) = rect.scale(getScale(size)).translate(getOffset(size))
}

@Composable
fun rememberWorldTransformState(
    zoom: Float = 1.0f,
    offset: Offset = Offset.Zero,
) = remember { WorldTransformState(zoom, offset) }

fun Modifier.transform(
    state: WorldTransformState
) = graphicsLayer {
    val scale = state.getScale(size)
    val center = size.center
    translationX = state.offset.x + center.x * scale
    translationY = state.offset.y + center.y * scale
    scaleX = scale
    scaleY = scale
}
