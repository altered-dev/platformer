@file:OptIn(ExperimentalContracts::class)

package me.altered.platformer.glfw.input

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

operator fun KeyMod.plus(key: Key): CompositeInput = CompositeInput.KeyWithMods(key, this)

operator fun KeyMod.plus(button: MouseButton): CompositeInput = CompositeInput.MouseButtonWithMods(button, this)

infix fun InputEvent.has(key: Key): Boolean {
    contract { returns(true) implies (this@has is InputEvent.Key) }
    return this is InputEvent.Key && this.key == key
}

infix fun InputEvent.has(button: MouseButton): Boolean {
    contract { returns(true) implies (this@has is InputEvent.MouseButton) }
    return this is InputEvent.MouseButton && this.button == button
}

infix fun InputEvent.has(input: CompositeInput): Boolean {
    contract { returns(true) implies (this@has is InputEvent.WithAction) }
    return when (this) {
        is InputEvent.Key -> input is CompositeInput.KeyWithMods &&
                key == input.key &&
                mods has input.mods
        is InputEvent.MouseButton -> input is CompositeInput.MouseButtonWithMods &&
                button == input.button &&
                mods has input.mods
        else -> false
    }
}

infix fun InputEvent.released(key: Key): Boolean {
    contract { returns(true) implies (this@released is InputEvent.Key) }
    return has(key) && action == Action.RELEASE
}

infix fun InputEvent.released(button: MouseButton): Boolean {
    contract { returns(true) implies (this@released is InputEvent.MouseButton) }
    return has(button) && action == Action.RELEASE
}

infix fun InputEvent.released(input: CompositeInput): Boolean {
    contract { returns(true) implies (this@released is InputEvent.WithAction) }
    return has(input) && action == Action.RELEASE
}

infix fun InputEvent.pressed(key: Key): Boolean {
    contract { returns(true) implies (this@pressed is InputEvent.Key) }
    return has(key) && action == Action.PRESS
}

infix fun InputEvent.pressed(button: MouseButton): Boolean {
    contract { returns(true) implies (this@pressed is InputEvent.MouseButton) }
    return has(button) && action == Action.PRESS
}

infix fun InputEvent.pressed(input: CompositeInput): Boolean {
    contract { returns(true) implies (this@pressed is InputEvent.WithAction) }
    return has(input) && action == Action.PRESS
}

infix fun InputEvent.repeated(key: Key): Boolean {
    contract { returns(true) implies (this@repeated is InputEvent.Key) }
    return has(key) && action == Action.REPEAT
}

infix fun InputEvent.repeated(input: CompositeInput): Boolean {
    contract { returns(true) implies (this@repeated is InputEvent.WithAction) }
    return has(input) && action == Action.REPEAT
}

fun InputEvent.cursorMoved(): Boolean {
    contract { returns(true) implies (this@cursorMoved is InputEvent.CursorMove) }
    return this is InputEvent.CursorMove
}

fun InputEvent.scrolled(): Boolean {
    contract { returns(true) implies (this@scrolled is InputEvent.Scroll) }
    return this is InputEvent.Scroll
}

fun InputEvent.cursorEntered(): Boolean {
    contract { returns(true) implies (this@cursorEntered is InputEvent.CursorEnter) }
    return this is InputEvent.CursorEnter && entered
}

fun InputEvent.cursorExited(): Boolean {
    contract { returns(true) implies (this@cursorExited is InputEvent.CursorEnter) }
    return this is InputEvent.CursorEnter && !entered
}
