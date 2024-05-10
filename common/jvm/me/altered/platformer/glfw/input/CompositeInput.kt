package me.altered.platformer.glfw.input

sealed interface CompositeInput {

    val mods: KeyMod

    data class KeyWithMods(val key: Key, override val mods: KeyMod) : CompositeInput

    data class MouseButtonWithMods(val button: MouseButton, override val mods: KeyMod) : CompositeInput
}
