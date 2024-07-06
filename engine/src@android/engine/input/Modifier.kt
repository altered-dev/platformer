package me.altered.platformer.engine.input

actual class Modifier(private val value: Int) {

    actual operator fun plus(other: Modifier) = Modifier(value or other.value)

    actual infix fun has(other: Modifier) = other.value == 0 || value and other.value != 0

    actual companion object {
        actual val None = Modifier(0)
        actual val Shift = Modifier(1)
        actual val Control = Modifier(2)
        actual val Alt = Modifier(4)
        actual val Super = Modifier(8)
    }
}
