package me.altered.platformer.timeline

class Range<T : Comparable<T>>(
    val start: Expression<T>,
    val end: Expression<T>,
) : Expression<ClosedRange<T>> {
    override fun eval(time: Float): ClosedRange<T> {
        return start.eval(time)..end.eval(time)
    }

    override fun toString(): String = "$start..$end"
}

class FloatRange(
    val start: Expression<Float>,
    val end: Expression<Float>,
) : Expression<ClosedFloatingPointRange<Float>> {
    override fun eval(time: Float): ClosedFloatingPointRange<Float> {
        return start.eval(time)..end.eval(time)
    }

    override fun toString(): String = "$start..$end"
}

operator fun <T : Comparable<T>> Expression<T>.rangeTo(other: Expression<T>) = Range(this, other)

operator fun Expression<Float>.rangeTo(other: Expression<Float>) = FloatRange(this, other)

class FloatWithin(left: Expression<Float>, right: Expression<ClosedFloatingPointRange<Float>>) : Binary<Float, ClosedFloatingPointRange<Float>, Boolean>(left, right) {
    override fun transform(left: Float, right: ClosedFloatingPointRange<Float>): Boolean = left in right
    override fun toString(): String = "$left in $right"
}

infix fun Expression<Float>.within(range: Expression<ClosedFloatingPointRange<Float>>): Expression<Boolean> = FloatWithin(this, range)
