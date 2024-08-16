package me.altered.platformer.timeline

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.koml.lerp

@Serializable
@SerialName("unaryMinus")
class FloatUnaryMinus(override val expression: Expression<Float>) : Unary<Float, Float>() {

    override fun transform(value: Float): Float = -value
    override fun toString(): String = "-$expression"
}

@Serializable
@SerialName("plus")
class FloatPlus(override val left: Expression<Float>, override val right: Expression<Float>) : Binary<Float, Float, Float>() {

    override fun transform(left: Float, right: Float): Float = left + right
    override fun toString(): String = "$left + $right"
}

@Serializable
@SerialName("minus")
class FloatMinus(override val left: Expression<Float>, override val right: Expression<Float>) : Binary<Float, Float, Float>() {

    override fun transform(left: Float, right: Float): Float = left - right
    override fun toString(): String = "$left - $right"
}

@Serializable
@SerialName("times")
class FloatTimes(override val left: Expression<Float>, override val right: Expression<Float>) : Binary<Float, Float, Float>() {

    override fun transform(left: Float, right: Float): Float = left * right
    override fun toString(): String = "$left * $right"
}

@Serializable
@SerialName("div")
class FloatDiv(override val left: Expression<Float>, override val right: Expression<Float>) : Binary<Float, Float, Float>() {

    override fun transform(left: Float, right: Float): Float = left / right
    override fun toString(): String = "$left / $right"
}

@Serializable
@SerialName("rem")
class FloatRem(override val left: Expression<Float>, override val right: Expression<Float>) : Binary<Float, Float, Float>() {

    override fun transform(left: Float, right: Float): Float = left % right
    override fun toString(): String = "$left % $right"
}

@Serializable
@SerialName("lerp")
class FloatLerp(
    private val from: Expression<Float>,
    private val to: Expression<Float>,
    private val t: Expression<Float>,
) : Expression<Float> {

    override fun eval(time: Float): Float = lerp(from.eval(time), to.eval(time), t.eval(time))

    override fun toString(): String = "lerp($from, $to, $t)"
}

/**
 * Returns this value.
 */
operator fun Expression<Float>.unaryPlus(): Expression<Float> = this

/**
 * Returns the negative of this value.
 */
operator fun Expression<Float>.unaryMinus(): Expression<Float> = FloatUnaryMinus(this)

/**
 * Adds the other value to this value.
 */
operator fun Expression<Float>.plus(other: Expression<Float>): Expression<Float> = FloatPlus(this, other)

/**
 * Subtracts the other value from this value.
 */
operator fun Expression<Float>.minus(other: Expression<Float>): Expression<Float> = FloatMinus(this, other)

/**
 * Multiplies this value by the other value.
 */
operator fun Expression<Float>.times(other: Expression<Float>): Expression<Float> = FloatTimes(this, other)

/**
 * Divides this value by the other value.
 */
operator fun Expression<Float>.div(other: Expression<Float>): Expression<Float> = FloatDiv(this, other)

/**
 * Calculates the remainder of truncating division of this value (dividend) by the other value (divisor).
 *
 * The result is either zero or has the same sign as the _dividend_ and has the absolute value less than the absolute value of the divisor
 */
operator fun Expression<Float>.rem(other: Expression<Float>): Expression<Float> = FloatRem(this, other)

/**
 * Linearly interpolates [t] between [from] and [to].
 *
 * @param from the lower bound
 * @param to the upper bound
 * @param t the lerp value, usually between 0 and 1
 *
 * @return [from] if [t] is 0, [to] if [t] is 1, everything in between otherwise
 */
fun lerp(from: Expression<Float>, to: Expression<Float>, t: Expression<Float>): Expression<Float> = FloatLerp(from, to, t)
