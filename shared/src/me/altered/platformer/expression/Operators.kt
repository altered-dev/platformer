package me.altered.platformer.expression

import kotlinx.serialization.Serializable

@Serializable
sealed class Unary<T, out TR> : Expression<TR> {
    abstract val expression: Expression<T>

    abstract fun transform(value: T): TR

    final override fun eval(time: Float): TR = transform(expression.eval(time))
}

@Serializable
sealed class Binary<L, R, out T> : Expression<T> {
    abstract val left: Expression<L>
    abstract val right: Expression<R>

    abstract fun transform(left: L, right: R): T

    final override fun eval(time: Float): T = transform(left.eval(time), right.eval(time))
}
