package me.altered.platformer.engine.node

open class Viewport(
    name: String = "Viewport",
    parent: Node? = null,
) : Node(name, parent) {

    @Suppress("LeakingThis")
    final override val viewport: Viewport = this


}
