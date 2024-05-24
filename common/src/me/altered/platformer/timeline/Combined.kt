package me.altered.platformer.timeline

class FloatSum(
    override val left: Expression<Float>,
    override val right: Expression<Float>,
) : Expression.Combined<Float>() {

    override val value: Float
        get() = left.value + right.value

    override fun toString() = "$left + $right"
}

operator fun Expression<Float>.plus(other: Expression<Float>) = FloatSum(this, other)

class FloatDifference(
    override val left: Expression<Float>,
    override val right: Expression<Float>,
) : Expression.Combined<Float>() {

    override val value: Float
        get() = left.value - right.value

    override fun toString() = "$left - $right"
}

operator fun Expression<Float>.minus(other: Expression<Float>) = FloatDifference(this, other)

class FloatProduct(
    override val left: Expression<Float>,
    override val right: Expression<Float>,
) : Expression.Combined<Float>() {

    override val value: Float
        get() = left.value * right.value

    override fun toString() = "$left * $right"
}

operator fun Expression<Float>.times(other: Expression<Float>) = FloatProduct(this, other)

class FloatFraction(
    override val left: Expression<Float>,
    override val right: Expression<Float>,
) : Expression.Combined<Float>() {

    override val value: Float
        get() = left.value / right.value

    override fun toString() = "$left / $right"
}

operator fun Expression<Float>.div(other: Expression<Float>) = FloatFraction(this, other)
