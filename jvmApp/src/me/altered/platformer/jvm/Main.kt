package me.altered.platformer.jvm

import me.altered.platformer.MainScene
import me.altered.platformer.engine.input.Action
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Modifier
import me.altered.platformer.engine.node.SceneManager
import me.altered.platformer.engine.util.enumValueOf
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import java.awt.Dimension
import java.awt.Point
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

fun main() {
    val skiaLayer = SkiaLayer()
    val sceneManager = SceneManager
    skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, sceneManager)
    skiaLayer.addMouseListener(object : MouseListener {
        override fun mouseClicked(p0: MouseEvent?) = Unit

        override fun mousePressed(p0: MouseEvent?) {
            if (p0 == null) return
            sceneManager.input(
                InputEvent.MouseEvent(
                    button = enumValueOf(p0.button),
                    x = p0.x.toFloat(),
                    y = p0.y.toFloat(),
                    action = Action.PRESS,
                    modifier = Modifier(p0.modifiersEx),
                )
            )
        }

        override fun mouseReleased(p0: MouseEvent?) {
            if (p0 == null) return
            sceneManager.input(
                InputEvent.MouseEvent(
                    button = enumValueOf(p0.button),
                    x = p0.x.toFloat(),
                    y = p0.y.toFloat(),
                    action = Action.RELEASE,
                    modifier = Modifier(p0.modifiersEx),
                )
            )
        }

        override fun mouseEntered(p0: MouseEvent?) {
            if (p0 == null) return
            sceneManager.input(InputEvent.CursorEnter(true))
        }

        override fun mouseExited(p0: MouseEvent?) {
            if (p0 == null) return
            sceneManager.input(InputEvent.CursorEnter(true))
        }

    })
    skiaLayer.addMouseMotionListener(object : MouseMotionListener {
        override fun mouseDragged(p0: MouseEvent?) = Unit

        override fun mouseMoved(p0: MouseEvent?) {
            if (p0 == null) return
            sceneManager.input(InputEvent.CursorMove(p0.x.toFloat(), p0.y.toFloat()))
        }
    })
    skiaLayer.addKeyListener(object : KeyListener {
        override fun keyTyped(p0: KeyEvent?) = Unit

        override fun keyPressed(p0: KeyEvent?) {
            if (p0 == null) return
            sceneManager.input(
                InputEvent.KeyEvent(
                    key = enumValueOf(p0.keyCode),
                    action = Action.PRESS,
                    modifier = Modifier(p0.modifiersEx),
                )
            )
        }

        override fun keyReleased(p0: KeyEvent?) {
            if (p0 == null) return
            sceneManager.input(
                InputEvent.KeyEvent(
                    key = enumValueOf(p0.keyCode),
                    action = Action.RELEASE,
                    modifier = Modifier(p0.modifiersEx),
                )
            )
        }
    })
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
