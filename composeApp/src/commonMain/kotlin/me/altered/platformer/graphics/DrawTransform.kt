package me.altered.platformer.graphics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawTransform
import me.altered.platformer.level.World
import me.altered.platformer.node.Node2D

fun DrawTransform.transform(node: Node2D) {
    translate(node.position.x, node.position.y)
    rotate(node.rotation)
    scale(node.scale.width, node.scale.height)
}

fun DrawTransform.transform(node: World) {
    val offset = with(node) { offset }
    val scale = with(node) { this@transform.scale }
    translate(offset.x, offset.y)
    scale(scale, scale, Offset.Zero)
}
