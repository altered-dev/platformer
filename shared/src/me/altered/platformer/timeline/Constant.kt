package me.altered.platformer.timeline

import kotlin.jvm.JvmInline

/**
 * An expression defined by a number that never changes.
 */
@JvmInline
value class Constant<out T>(private val value: T) : Expression<T> {

    override fun eval(time: Float): T = value

    override fun toString() = value.toString()
}

/**
 * Creates an expression that is always resolved to [value].
 *
 * @param T the type of the expression.
 */
fun <T> const(value: T): Expression<T> = Constant(value)
