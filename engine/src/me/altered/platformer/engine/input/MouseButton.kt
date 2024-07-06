package me.altered.platformer.engine.input

expect enum class MouseButton {
    N1,
    N2,
    N3,
    N4,
    N5,
    ;
    companion object
}

val MouseButton.Companion.Left get() = MouseButton.N1
val MouseButton.Companion.Middle get() = MouseButton.N2
val MouseButton.Companion.Right get() = MouseButton.N3
