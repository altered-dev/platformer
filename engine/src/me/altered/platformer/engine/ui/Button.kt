package me.altered.platformer.engine.ui

import me.altered.koml.Vector2f
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Left
import me.altered.platformer.engine.input.MouseButton
import me.altered.platformer.engine.input.cursorMoved
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.graphics.contains

class Button(
    text: String = "Button",
    parent: Node? = null,
    width: Size = 128.px,
    height: Size = 32.px,
    padding: Insets = padding(),
    anchor: Vector2f = Vector2f(0.0f, 0.0f),
    var onClick: () -> Unit = {},
    enabled: Boolean = true,
) : UiNode(text, parent, width, height, padding, anchor) {

    private val textNode = +Text(text, width = expand(), height = wrap(), anchor = Vector2f(0.5f, 0.5f))

    var text by textNode::text

    private var state = if (enabled) State.Idle else State.Disabled
        set(value) {
            field = value
        }

    var enabled = enabled
        set(value) {
            state = if (value) State.Idle else State.Disabled
            field = value
        }

    override fun input(event: InputEvent) {
        when {
            event.cursorMoved() -> {
                if (state == State.Idle && bounds.contains(event.x, event.y)) {
                    state = State.Hovered
                } else if (state != State.Idle && state != State.Disabled && !bounds.contains(event.x, event.y)) {
                    state = State.Idle
                }
            }
            event pressed MouseButton.Left -> {
                if (state == State.Hovered) {
                    state = State.Pressed
                }
            }
            event released MouseButton.Left -> {
                if (state == State.Pressed) {
                    onClick()
                    state = State.Hovered
                }
            }
        }
    }

    enum class State { Idle, Hovered, Pressed, Disabled }
}
