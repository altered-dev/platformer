package me.altered.platformer.engine.input

sealed interface CompositeInput {

    val modifier: Modifier

    data class KeyWithMods(val key: Key, override val modifier: Modifier) : CompositeInput

    data class MouseButtonWithMods(val button: MouseButton, override val modifier: Modifier) : CompositeInput
}
