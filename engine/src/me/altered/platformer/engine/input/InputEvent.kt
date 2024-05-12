package me.altered.platformer.engine.input

sealed interface InputEvent {

    sealed interface WithAction : InputEvent {
        val action: Action
    }

    data class KeyEvent(val key: Key, override val action: Action, val modifier: Modifier) : WithAction

    data class MouseEvent(
        val button: MouseButton,
        val x: Float,
        val y: Float,
        override val action: Action,
        val modifier: Modifier
    ) : WithAction

    data class CursorMove(val x: Float, val y: Float) : InputEvent

    data class Scroll(val dx: Float, val dy: Float, val modifier: Modifier) : InputEvent

    data class CursorEnter(val entered: Boolean) : InputEvent

    data class WindowResize(val width: Float, val height: Float) : InputEvent
}
