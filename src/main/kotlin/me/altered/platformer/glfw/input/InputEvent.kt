package me.altered.platformer.glfw.input

import me.altered.platformer.glfw.input.Key as GlfwKey
import me.altered.platformer.glfw.input.MouseButton as GlfwMouseButton

sealed interface InputEvent {

    sealed interface WithAction : InputEvent {
        val action: Action
    }

    data class Key(val key: GlfwKey, val scancode: Int, override val action: Action, val mods: KeyMod) : WithAction

    data class MouseButton(val button: GlfwMouseButton, override val action: Action, val mods: KeyMod) : WithAction

    data class CursorMove(val x: Double, val y: Double) : InputEvent

    data class Scroll(val dx: Double, val dy: Double) : InputEvent

    data class CursorEnter(val entered: Boolean) : InputEvent
}
