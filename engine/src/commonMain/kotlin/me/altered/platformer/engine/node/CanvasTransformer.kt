package me.altered.platformer.engine.node

import androidx.compose.ui.graphics.drawscope.DrawTransform

fun interface CanvasTransformer {

    fun DrawTransform.transform()
}
