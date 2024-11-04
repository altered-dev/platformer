package me.altered.platformer.engine.node

import androidx.compose.ui.graphics.drawscope.DrawTransform

fun DrawTransform.transform(transformer: CanvasTransformer) {
    with(transformer) { transform() }
}
