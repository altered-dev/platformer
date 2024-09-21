package me.altered.platformer.engine.node

import me.altered.koml.Vector2f
import me.altered.platformer.engine.graphics.emptyRect
import org.jetbrains.skia.Rect

open class Viewport(
    name: String = "Viewport",
    parent: Node? = null,
    open var size: Float = 1.0f,
    open val offset: Vector2f = Vector2f(),
) : Node(name, parent) {

    @Suppress("LeakingThis")
    final override val viewport: Viewport = this

    val windowBounds: Rect
        get() {
            val window = window ?: return emptyRect()
            return Rect.makeWH(window.width.toFloat(), window.height.toFloat()).screenToWorld()
        }
}
