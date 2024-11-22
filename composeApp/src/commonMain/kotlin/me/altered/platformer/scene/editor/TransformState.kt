package me.altered.platformer.scene.editor

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

class TransformState(
    zoom: Float,
    offset: Offset,
) {

    var zoom by mutableFloatStateOf(zoom)
    var offset by mutableStateOf(offset)

    fun getOffset(size: Size) = offset + size.center

    fun getScale(size: Size) = zoom * size.height * WorldScale

    fun Offset.screenToWorld(size: Size) = (this - getOffset(size)) / getScale(size)

    fun Offset.worldToScreen(size: Size) = this * getScale(size) + getOffset(size)

    fun Rect.screenToWorld(size: Size) = translate(-getOffset(size)).scale(1.0f / getScale(size))

    fun Rect.worldToScreen(size: Size) = scale(getScale(size)).translate(getOffset(size))
}

val TransformState.screenToWorld: Offset.(Size) -> Offset
    get() = { screenToWorld(it) }

val TransformState.worldToScreen: Offset.(Size) -> Offset
    get() = { worldToScreen(it) }

val TransformState.rectScreenToWorld: Rect.(Size) -> Rect
    get() = { screenToWorld(it) }

val TransformState.rectWorldToScreen: Rect.(Size) -> Rect
    get() = { worldToScreen(it) }

@Composable
fun rememberTransformState(
    zoom: Float = 1.0f,
    offset: Offset = Offset.Zero,
) = remember { TransformState(zoom, offset) }

fun Modifier.transform(
    state: TransformState
) = graphicsLayer {
    val scale = state.getScale(size)
    val center = size.center
    translationX = state.offset.x + center.x * scale
    translationY = state.offset.y + center.y * scale
    scaleX = scale
    scaleY = scale
}
