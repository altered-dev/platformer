package me.altered.platformer.engine.node

import me.altered.koml.Vector2f
import me.altered.platformer.engine.graphics.Color
import org.jetbrains.skiko.SkiaLayer
import javax.swing.JFrame
import javax.swing.WindowConstants

actual class Window(
    name: String = "Window",
    parent: Node? = null,
    viewportSize: Float = 1.0f,
    offset: Vector2f = Vector2f(),
    actual var background: Color = Color.White,
) : Viewport(name, parent, viewportSize, offset) {

    private val frame = JFrame(name).apply {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        size = toolkit.screenSize
//        preferredSize = Dimension(width, height)
//        setLocationRelativeTo(null)
    }

    override var name: String
        get() = frame.title
        set(value) { frame.title = value }

    actual override val window: Window
        get() = this

    actual var isVisible: Boolean
        get() = frame.isVisible
        set(value) { frame.isVisible = value }

    fun setSize(width: Int, height: Int) = frame.setSize(width, height)

    actual var width: Int
        get() = frame.contentPane.width
        set(value) = frame.setSize(value, height)

    actual var height: Int
        get() = frame.contentPane.height
        set(value) = frame.setSize(width, value)

    actual fun attachSkiaLayer(layer: SkiaLayer) {
        layer.attachTo(frame.contentPane)
    }

    fun pack() = frame.pack()
}
