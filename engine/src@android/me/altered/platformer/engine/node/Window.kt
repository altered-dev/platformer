package me.altered.platformer.engine.node

import android.view.View
import android.widget.FrameLayout
import me.altered.koml.Vector2f
import me.altered.platformer.engine.graphics.Color
import org.jetbrains.skiko.SkiaLayer

actual class Window(
    name: String = "Window",
    parent: Node? = null,
    viewportSize: Float = 1.0f,
    offset: Vector2f = Vector2f(),
    private val frameLayout: FrameLayout,
    actual var background: Color = Color.White,
) : Viewport(name, parent, viewportSize, offset) {

    actual override val window: Window
        get() = this

    actual var isVisible: Boolean
        get() = this.frameLayout.visibility == View.VISIBLE
        set(value) {
            this.frameLayout.visibility = if (value) View.VISIBLE else View.GONE
        }

    actual var width: Int
        get() = this.frameLayout.width
        set(_) = Unit

    actual var height: Int
        get() = this.frameLayout.height
        set(_) = Unit

    actual fun attachSkiaLayer(layer: SkiaLayer) {
//        layer.attachTo(this.frame)
    }
}
