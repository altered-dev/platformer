package me.altered.platformer.engine.node

import android.view.View
import android.widget.FrameLayout
import me.altered.koml.Vector2f
import org.jetbrains.skiko.SkiaLayer

actual class Window actual constructor(
    name: String,
    parent: Node?,
    viewportSize: Float,
    offset: Vector2f,
) : Viewport(name, parent, viewportSize, offset) {

    private lateinit var frame: FrameLayout

    constructor(
        name: String = "Window",
        parent: Node? = null,
        viewportSize: Float,
        offset: Vector2f = Vector2f(),
        frameLayout: FrameLayout,
    ) : this(name, parent, viewportSize, offset) {
        this.frame = frameLayout
    }

    actual override val window: Window
        get() = this

    actual var isVisible: Boolean
        get() = frame.visibility == View.VISIBLE
        set(value) {
            frame.visibility = if (value) View.VISIBLE else View.GONE
        }

    actual var width: Int
        get() = frame.width
        set(_) = Unit

    actual var height: Int
        get() = frame.height
        set(_) = Unit

    actual fun attachSkiaLayer(layer: SkiaLayer) {
        layer.attachTo(layer)
    }
}