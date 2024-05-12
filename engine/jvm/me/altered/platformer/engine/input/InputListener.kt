package me.altered.platformer.engine.input

import me.altered.platformer.engine.util.enumValueOf
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
    override fun mouseClicked(e: MouseEvent) = Unit

    override fun mousePressed(e: MouseEvent) {
        handler.input(
            InputEvent.MouseEvent(
                button = enumValueOf(e.button),
                x = e.x.toFloat(),
                y = e.y.toFloat(),
                action = Action.PRESS,
                modifier = Modifier(e.modifiersEx),
            )
        )
    }

    override fun mouseReleased(p0: MouseEvent) {
        handler.input(
            InputEvent.MouseEvent(
                button = enumValueOf(p0.button),
                x = p0.x.toFloat(),
                y = p0.y.toFloat(),
                action = Action.RELEASE,
                modifier = Modifier(p0.modifiersEx),
            )
        )
    }

    override fun mouseEntered(p0: MouseEvent) {
        handler.input(InputEvent.CursorEnter(true))
    }

    override fun mouseExited(p0: MouseEvent) {
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

    override fun mouseDragged(p0: MouseEvent) = Unit

    override fun mouseMoved(p0: MouseEvent) {
        handler.input(InputEvent.CursorMove(p0.x.toFloat(), p0.y.toFloat()))
    }

    override fun keyTyped(p0: KeyEvent) = Unit

    // workaround because this is weird
    private val keyMap = mutableSetOf<Key>()

    override fun keyPressed(p0: KeyEvent) {
        val key = enumValueOf<Key>(p0.keyCode)
        if (key in keyMap) return
        keyMap += key
        handler.input(
            InputEvent.KeyEvent(
                key = key,
                action = Action.PRESS,
                modifier = Modifier(p0.modifiersEx),
            )
        )
    }

    override fun keyReleased(p0: KeyEvent) {
        val key = enumValueOf<Key>(p0.keyCode)
        keyMap -= key
        handler.input(
            InputEvent.KeyEvent(
                key = key,
                action = Action.RELEASE,
                modifier = Modifier(p0.modifiersEx),
            )
        )
    }
}
