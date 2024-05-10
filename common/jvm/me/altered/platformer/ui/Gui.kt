package me.altered.platformer.ui

import me.altered.platformer.node.Node

class Gui(
    override val name: String = "gui",
) : Node() {


}

inline fun Gui(name: String = "gui", block: BoxScope.() -> Unit): Gui {

    return Gui(name)
}
