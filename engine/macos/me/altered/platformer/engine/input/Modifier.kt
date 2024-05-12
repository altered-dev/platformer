package me.altered.platformer.engine.input

import platform.AppKit.NSAlternateKeyMask
import platform.AppKit.NSCommandKeyMask
import platform.AppKit.NSControlKeyMask
import platform.AppKit.NSFunctionKeyMask
import platform.AppKit.NSShiftKeyMask

actual class Modifier(private val flags: ULong) {
    actual operator fun plus(other: Modifier) = Modifier(flags or other.flags)

    actual infix fun has(other: Modifier) = other.flags == 0UL || flags and other.flags != 0UL

    actual companion object {
        actual val NONE = Modifier(0UL)
        actual val SHIFT = Modifier(NSShiftKeyMask)
        actual val CONTROL = Modifier(NSControlKeyMask)
        actual val ALT = Modifier(NSAlternateKeyMask)
        actual val SUPER = Modifier(ULong.MAX_VALUE) // TODO: what is a super key on mac?
        val COMMAND = Modifier(NSCommandKeyMask)
        val FUNCTION = Modifier(NSFunctionKeyMask)
    }
}
