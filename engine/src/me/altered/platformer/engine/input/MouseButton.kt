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

val MouseButton.Companion.LEFT get() = MouseButton.N1
val MouseButton.Companion.MIDDLE get() = MouseButton.N2
val MouseButton.Companion.RIGHT get() = MouseButton.N3
