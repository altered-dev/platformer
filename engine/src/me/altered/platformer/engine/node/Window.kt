package me.altered.platformer.engine.node

import org.jetbrains.skiko.SkiaLayer

expect class Window(
    name: String = "Window",
    parent: Node? = null,
    width: Int,
    height: Int,
) : Viewport {

    override val window: Window

    var isVisible: Boolean

    fun attachSkiaLayer(layer: SkiaLayer)
}
