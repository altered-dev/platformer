package me.altered.platformer.engine.input

import java.awt.event.InputEvent.*

actual class Modifier(private val value: Int) {

    actual operator fun plus(other: Modifier) = Modifier(value or other.value)

    actual infix fun has(other: Modifier) = other.value == 0 || value and other.value != 0

    actual companion object {
        actual val NONE = Modifier(0)
        actual val SHIFT = Modifier(SHIFT_DOWN_MASK)
        actual val CONTROL = Modifier(CTRL_DOWN_MASK)
        actual val ALT = Modifier(ALT_DOWN_MASK)
        actual val SUPER = Modifier(META_DOWN_MASK)
    }
}
