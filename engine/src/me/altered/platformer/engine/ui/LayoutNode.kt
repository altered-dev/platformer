package me.altered.platformer.engine.ui

import me.altered.platformer.engine.node.Node

abstract class LayoutNode(
    name: String = "LayoutNode",
    parent: Node? = null,
    width: Size,
    height: Size,
    children: Iterable<UiNode2>,
) : UiNode2(name, parent, width, height) {

    init {
        addChildren(children)
    }


}