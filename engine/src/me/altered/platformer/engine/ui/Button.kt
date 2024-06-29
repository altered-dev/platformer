package me.altered.platformer.engine.ui

import me.altered.koml.Vector2f
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.LEFT
import me.altered.platformer.engine.input.MouseButton
import me.altered.platformer.engine.input.cursorMoved
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.util.contains

class Button(
    text: String = "Button",
    parent: Node? = null,
    width: Size = 128.px,
    height: Size = 32.px,
    padding: Insets = none,
    anchor: Vector2f = Vector2f(0.0f, 0.0f),
    var onClick: () -> Unit = {},
    enabled: Boolean = true,
) : UiNode(text, parent, width, height, padding, anchor) {

    private val textNode = +Text(text, width = expand, height = wrap)

    var text by textNode::text

    private var state = if (enabled) State.IDLE else State.DISABLED
        set(value) {
            field = value
        }

    var enabled = enabled
        set(value) {
            state = if (value) State.IDLE else State.DISABLED
            field = value
        }

    override fun input(event: InputEvent) {
        when {
            event.cursorMoved() -> {
                if (state == State.IDLE && bounds.contains(event.x, event.y)) {
                    state = State.HOVERED
                } else if (state != State.IDLE && state != State.DISABLED && !bounds.contains(event.x, event.y)) {
                    state = State.IDLE
                }
            }
            event pressed MouseButton.LEFT -> {
                if (state == State.HOVERED) {
                    state = State.PRESSED
                }
            }
            event released MouseButton.LEFT -> {
                if (state == State.PRESSED) {
                    onClick()
                    state = State.HOVERED
                }
            }
        }
    }

    enum class State { IDLE, HOVERED, PRESSED, DISABLED }
}