package me.altered.platformer.ui

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Font
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Rect
import me.altered.platformer.glfw.input.Action
import me.altered.platformer.glfw.input.InputEvent
import me.altered.platformer.glfw.input.MouseButton
import me.altered.platformer.io.font
import me.altered.platformer.node.Node
import me.altered.platformer.node.SceneManager.defer
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.buildPaint
import me.altered.platformer.skija.color
import me.altered.platformer.skija.contains

class Button(
    private val rect: Rect = Rect.makeWH(0.0f, 0.0f),
    override val name: String = "Button",
    private val onClick: () -> Unit = {},
) : Node() {

    private var state = State.IDLE

    private val paint = buildPaint {
        color4f = color(0xFF80FFBF)
    }

    private val textPaint = buildPaint {
        color4f = Color.black
    }

    override fun draw(canvas: Canvas): Unit = canvas.run {
        drawRect(rect, paint)
        drawString(name, rect.left + 16.0f, rect.top + 16.0f, font, textPaint)
    }

    override fun input(event: InputEvent): Boolean {
        return when (event) {
            is InputEvent.CursorMove -> {
                if (rect.contains(event.x, event.y)) {
                    if (state != State.IDLE) return true
                    state = State.HOVERED
                    paint.color4f = color(0xFF99FFCC)
                    true
                } else {
                    if (state == State.IDLE) return false
                    state = State.IDLE
                    paint.color4f = color(0xFF80FFBF)
                    false
                }
            }
            is InputEvent.MouseButton -> when {
                state == State.IDLE || event.button != MouseButton.LEFT -> false

                state == State.HOVERED && event.action == Action.PRESS -> {
                    state = State.PRESSED
                    paint.color4f = color(0xFF73E5AC)
                    true
                }

                state == State.PRESSED && event.action == Action.RELEASE -> {
                    state = State.HOVERED
                    paint.color4f = color(0xFF99FFCC)
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

        private val font = font("fonts/Inter-Regular.ttf", 13.0f)
    }
}
