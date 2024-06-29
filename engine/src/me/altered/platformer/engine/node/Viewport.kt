package me.altered.platformer.engine.node

import me.altered.koml.Vector2f

open class Viewport(
    name: String = "Viewport",
    parent: Node? = null,
    var size: Float,
    val offset: Vector2f = Vector2f(),
) : Node(name, parent) {

    @Suppress("LeakingThis")
    final override val viewport: Viewport = this


}
