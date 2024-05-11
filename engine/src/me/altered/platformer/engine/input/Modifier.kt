package me.altered.platformer.engine.input

expect class Modifier {

    operator fun plus(other: Modifier): Modifier

    infix fun has(other: Modifier): Boolean

    companion object {
        val NONE: Modifier
        val SHIFT: Modifier
        val CONTROL: Modifier
        val ALT: Modifier
        val SUPER: Modifier
    }
}
