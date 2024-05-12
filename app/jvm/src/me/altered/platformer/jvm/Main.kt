package me.altered.platformer.jvm

import me.altered.platformer.MainScene
import me.altered.platformer.engine.input.InputListener
import me.altered.platformer.engine.node.SceneManager
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import java.awt.Dimension
import java.awt.Point
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

fun main() {
    val sceneManager = SceneManager
    val listener = InputListener(sceneManager)
    val skiaLayer = SkiaLayer()
    skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, sceneManager)
    skiaLayer.addMouseListener(listener)
    skiaLayer.addMouseMotionListener(listener)
    skiaLayer.addMouseWheelListener(listener)
    skiaLayer.addKeyListener(listener)
    SwingUtilities.invokeLater {
        val window = JFrame("hello world").apply {
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            preferredSize = Dimension(800, 600)
            location = Point(500, 500)
        }
        skiaLayer.attachTo(window.contentPane)
        sceneManager.scene = MainScene()
        skiaLayer.needRedraw()
        window.pack()
        window.isVisible = true
    }
}
