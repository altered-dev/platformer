package me.altered.platformer.timeline

abstract class Unary<T, TR>(protected val expression: Expression<T>) : Expression<TR> {

    abstract fun transform(value: T): TR

    final override fun eval(time: Float): TR = transform(expression.eval(time))
}

abstract class Binary<L, R, T>(
    protected val left: Expression<L>,
    protected val right: Expression<R>,
) : Expression<T> {

    abstract fun transform(left: L, right: R): T

    final override fun eval(time: Float): T = transform(left.eval(time), right.eval(time))
}
