package me.altered.platformer.engine.input

import me.altered.platformer.engine.util.enumValueOf
import org.jetbrains.skiko.SkiaLayer
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.awt.event.MouseWheelEvent
import java.awt.event.MouseWheelListener

actual class InputListener actual constructor(
    private val handler: InputHandler,
) : MouseListener, MouseWheelListener, MouseMotionListener, KeyListener {

    actual fun listenSkiaLayer(skiaLayer: SkiaLayer) {
        skiaLayer.addMouseListener(this)
        skiaLayer.addMouseMotionListener(this)
        skiaLayer.addMouseWheelListener(this)
        skiaLayer.addKeyListener(this)
    }

    override fun mouseClicked(e: MouseEvent) = Unit

    override fun mousePressed(e: MouseEvent) {
        handler.input(
            InputEvent.MouseEvent(
                button = enumValueOf(e.button),
                x = e.x.toFloat(),
                y = e.y.toFloat(),
                action = Action.Press,
                modifier = Modifier(e.modifiersEx),
            )
        )
    }

    override fun mouseReleased(e: MouseEvent) {
        handler.input(
            InputEvent.MouseEvent(
                button = enumValueOf(e.button),
                x = e.x.toFloat(),
                y = e.y.toFloat(),
                action = Action.Release,
                modifier = Modifier(e.modifiersEx),
            )
        )
    }

    override fun mouseEntered(e: MouseEvent) {
        handler.input(InputEvent.CursorEnter(true))
    }

    override fun mouseExited(e: MouseEvent) {
        handler.input(InputEvent.CursorEnter(false))
    }

    override fun mouseWheelMoved(e: MouseWheelEvent) {
        handler.input(
            InputEvent.Scroll(
                dx = 0.0f,
                dy = e.wheelRotation.toFloat(),
                modifier = Modifier(e.modifiersEx)
            )
        )
    }

    override fun mouseDragged(e: MouseEvent) {
        handler.input(InputEvent.CursorMove(e.x.toFloat(), e.y.toFloat()))
    }

    override fun mouseMoved(e: MouseEvent) {
        handler.input(InputEvent.CursorMove(e.x.toFloat(), e.y.toFloat()))
    }

    override fun keyTyped(e: KeyEvent) = Unit

    // workaround because this is weird
    private val keyMap = mutableSetOf<Key>()

    override fun keyPressed(e: KeyEvent) {
        val key = enumValueOf<Key>(e.keyCode)
        if (key in keyMap) return
        keyMap += key
        handler.input(
            InputEvent.KeyEvent(
                key = key,
                action = Action.Press,
                modifier = Modifier(e.modifiersEx),
            )
        )
    }

    override fun keyReleased(e: KeyEvent) {
        val key = enumValueOf<Key>(e.keyCode)
        keyMap -= key
        handler.input(
            InputEvent.KeyEvent(
                key = key,
                action = Action.Release,
                modifier = Modifier(e.modifiersEx),
            )
        )
    }
}
