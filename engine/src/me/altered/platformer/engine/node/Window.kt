package me.altered.platformer.engine.node

import me.altered.koml.Vector2f
import org.jetbrains.skiko.SkiaLayer

expect class Window(
    name: String = "Window",
    parent: Node? = null,
    width: Int,
    height: Int,
    viewportSize: Float,
    offset: Vector2f = Vector2f(),
) : Viewport {

    override val window: Window

    var isVisible: Boolean

    fun attachSkiaLayer(layer: SkiaLayer)
}
