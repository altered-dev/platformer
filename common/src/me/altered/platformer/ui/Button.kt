package me.altered.platformer.ui

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect
import me.altered.platformer.engine.input.Action
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.LEFT
import me.altered.platformer.engine.input.MouseButton
import me.altered.platformer.engine.io.font
import me.altered.platformer.engine.io.resource
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.SceneManager.defer
import me.altered.platformer.skija.buildPaint
import me.altered.platformer.skija.invoke
import me.altered.platformer.skija.contains
import org.jetbrains.skia.Color

class Button(
    private val rect: Rect = Rect.makeWH(0.0f, 0.0f),
    override val name: String = "Button",
    private val onClick: () -> Unit = {},
) : Node(name) {

    private var state = State.IDLE

    private val paint = buildPaint {
        color = Color(0xFF80FFBF)
    }

    private val textPaint = buildPaint {
        color = Color.BLACK
    }

    override fun draw(canvas: Canvas, width: Float, height: Float): Unit = canvas.run {
        drawRect(rect, paint)
        drawString(name, rect.left + 16.0f, rect.top + 16.0f, font, textPaint)
    }

    override fun input(event: InputEvent) {
        when (event) {
            is InputEvent.CursorMove -> {
                if (rect.contains(event.x, event.y)) {
                    if (state != State.IDLE) return // true
                    state = State.HOVERED
                    paint.color = Color(0xFF99FFCC)
                    true
                } else {
                    if (state == State.IDLE) return // false
                    state = State.IDLE
                    paint.color = Color(0xFF80FFBF)
                    false
                }
            }
            is InputEvent.MouseEvent -> when {
                state == State.IDLE || event.button != MouseButton.LEFT -> false

                state == State.HOVERED && event.action == Action.PRESS -> {
                    state = State.PRESSED
                    paint.color = Color(0xFF73E5AC)
                    true
                }

                state == State.PRESSED && event.action == Action.RELEASE -> {
                    state = State.HOVERED
                    paint.color = Color(0xFF99FFCC)
                    defer { onClick() }
                    true
                }

                else -> false
            }
            else -> false
        }
    }

    enum class State { IDLE, HOVERED, PRESSED }

    companion object {

        private val font = font(resource("fonts/Inter-Regular.ttf"), 13.0f)
    }
}
