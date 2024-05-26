package me.altered.platformer.engine.input

actual class Modifier(private val value: Int) {

    actual operator fun plus(other: Modifier) = Modifier(value or other.value)

    actual infix fun has(other: Modifier) = other.value == 0 || value and other.value != 0

    actual companion object {
        actual val NONE = Modifier(0)
        actual val SHIFT = Modifier(1)
        actual val CONTROL = Modifier(2)
        actual val ALT = Modifier(4)
        actual val SUPER = Modifier(8)
    }
}
