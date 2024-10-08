@file:OptIn(ExperimentalContracts::class)

package me.altered.platformer.engine.input

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

operator fun Modifier.plus(key: Key): CompositeInput = CompositeInput.KeyWithMods(key, this)

operator fun Modifier.plus(button: MouseButton): CompositeInput = CompositeInput.MouseButtonWithMods(button, this)

infix fun InputEvent.has(key: Key): Boolean {
    contract { returns(true) implies (this@has is InputEvent.KeyEvent) }
    return this is InputEvent.KeyEvent && this.key == key
}

infix fun InputEvent.has(button: MouseButton): Boolean {
    contract { returns(true) implies (this@has is InputEvent.MouseEvent) }
    return this is InputEvent.MouseEvent && this.button == button
}

infix fun InputEvent.has(input: CompositeInput): Boolean {
    contract { returns(true) implies (this@has is InputEvent.WithAction) }
    return when (this) {
        is InputEvent.KeyEvent -> input is CompositeInput.KeyWithMods &&
                key == input.key &&
                modifier has input.modifier
        is InputEvent.MouseEvent -> input is CompositeInput.MouseButtonWithMods &&
                button == input.button &&
                modifier has input.modifier
        else -> false
    }
}

infix fun InputEvent.released(key: Key): Boolean {
    contract { returns(true) implies (this@released is InputEvent.KeyEvent) }
    return has(key) && action == Action.Release
}

infix fun InputEvent.released(button: MouseButton): Boolean {
    contract { returns(true) implies (this@released is InputEvent.MouseEvent) }
    return has(button) && action == Action.Release
}

infix fun InputEvent.released(input: CompositeInput): Boolean {
    contract { returns(true) implies (this@released is InputEvent.WithAction) }
    return has(input) && action == Action.Release
}

infix fun InputEvent.pressed(key: Key): Boolean {
    contract { returns(true) implies (this@pressed is InputEvent.KeyEvent) }
    return has(key) && action == Action.Press
}

infix fun InputEvent.pressed(button: MouseButton): Boolean {
    contract { returns(true) implies (this@pressed is InputEvent.MouseEvent) }
    return has(button) && action == Action.Press
}

infix fun InputEvent.pressed(input: CompositeInput): Boolean {
    contract { returns(true) implies (this@pressed is InputEvent.WithAction) }
    return has(input) && action == Action.Press
}

infix fun InputEvent.repeated(key: Key): Boolean {
    contract { returns(true) implies (this@repeated is InputEvent.KeyEvent) }
    return has(key) && action == Action.Repeat
}

infix fun InputEvent.repeated(input: CompositeInput): Boolean {
    contract { returns(true) implies (this@repeated is InputEvent.WithAction) }
    return has(input) && action == Action.Repeat
}

fun InputEvent.cursorMoved(): Boolean {
    contract { returns(true) implies (this@cursorMoved is InputEvent.CursorMove) }
    return this is InputEvent.CursorMove
}

fun InputEvent.scrolled(): Boolean {
    contract { returns(true) implies (this@scrolled is InputEvent.Scroll) }
    return this is InputEvent.Scroll
}

infix fun InputEvent.scrolledWith(modifier: Modifier): Boolean {
    contract { returns(true) implies (this@scrolledWith is InputEvent.Scroll) }
    return this is InputEvent.Scroll && this.modifier has modifier
}

fun InputEvent.cursorEntered(): Boolean {
    contract { returns(true) implies (this@cursorEntered is InputEvent.CursorEnter) }
    return this is InputEvent.CursorEnter && entered
}

fun InputEvent.cursorExited(): Boolean {
    contract { returns(true) implies (this@cursorExited is InputEvent.CursorEnter) }
    return this is InputEvent.CursorEnter && !entered
}
