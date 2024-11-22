package me.altered.platformer.engine.node

import androidx.compose.ui.graphics.drawscope.DrawScope

open class CanvasNode(
    name: String = "CanvasNode",
    parent: Node? = null,
) : Node(name, parent) {

    open fun DrawScope.draw() = Unit
}

fun CanvasNode.draw(scope: DrawScope) = scope.draw()
