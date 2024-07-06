package me.altered.platformer.engine.input

expect class Modifier {

    operator fun plus(other: Modifier): Modifier

    infix fun has(other: Modifier): Boolean

    companion object {
        val None: Modifier
        val Shift: Modifier
        val Control: Modifier
        val Alt: Modifier
        val Super: Modifier
    }
}
