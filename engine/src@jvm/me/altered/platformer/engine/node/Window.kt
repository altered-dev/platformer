package me.altered.platformer.engine.node

import org.jetbrains.skiko.SkiaLayer
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.WindowConstants

actual class Window actual constructor(
    name: String,
    parent: Node?,
    width: Int,
    height: Int,
) : Viewport(name, parent) {

    private val frame = JFrame(name).apply {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        preferredSize = Dimension(width, height)
        setLocationRelativeTo(null)
    }

    override var name: String
        get() = frame.title
        set(value) { frame.title = value }

    actual override val window: Window
        get() = this

    actual var isVisible: Boolean
        get() = frame.isVisible
        set(value) { frame.isVisible = value }

    var width: Int
        get() = frame.width
        set(value) = frame.setSize(value, height)

    var height: Int
        get() = frame.height
        set(value) = frame.setSize(width, value)

    actual fun attachSkiaLayer(layer: SkiaLayer) {
        layer.attachTo(frame.contentPane)
    }

    fun pack() = frame.pack()
}