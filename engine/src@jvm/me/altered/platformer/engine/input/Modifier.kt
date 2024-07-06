package me.altered.platformer.engine.input

import java.awt.event.InputEvent.*

actual class Modifier(private val value: Int) {

    actual operator fun plus(other: Modifier) = Modifier(value or other.value)

    actual infix fun has(other: Modifier) = value and other.value == other.value

    actual companion object {
        actual val None = Modifier(0)
        actual val Shift = Modifier(SHIFT_DOWN_MASK)
        actual val Control = Modifier(CTRL_DOWN_MASK)
        actual val Alt = Modifier(ALT_DOWN_MASK)
        actual val Super = Modifier(META_DOWN_MASK)
    }
}
