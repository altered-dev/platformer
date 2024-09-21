package me.altered.platformer.expression

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("eq")
class FloatEq(override val left: Expression<Float>, override val right: Expression<Float>) : Binary<Float, Float, Boolean>() {

    override fun transform(left: Float, right: Float): Boolean = left == right
    override fun toString(): String = "$left == $right"
}

@Serializable
@SerialName("neq")
class FloatNeq(override val left: Expression<Float>, override val right: Expression<Float>) : Binary<Float, Float, Boolean>() {

    override fun transform(left: Float, right: Float): Boolean = left != right
    override fun toString(): String = "$left != $right"
}

@Serializable
@SerialName("greater")
class FloatGreater(override val left: Expression<Float>, override val right: Expression<Float>) : Binary<Float, Float, Boolean>() {

    override fun transform(left: Float, right: Float): Boolean = left > right
    override fun toString(): String = "$left > $right"
}

@Serializable
@SerialName("greaterEq")
class FloatGreaterEq(override val left: Expression<Float>, override val right: Expression<Float>) : Binary<Float, Float, Boolean>() {

    override fun transform(left: Float, right: Float): Boolean = left >= right
    override fun toString(): String = "$left >= $right"
}

@Serializable
@SerialName("less")
class FloatLess(override val left: Expression<Float>, override val right: Expression<Float>) : Binary<Float, Float, Boolean>() {

    override fun transform(left: Float, right: Float): Boolean = left < right
    override fun toString(): String = "$left < $right"
}

@Serializable
@SerialName("lessEq")
class FloatLessEq(override val left: Expression<Float>, override val right: Expression<Float>) : Binary<Float, Float, Boolean>() {

    override fun transform(left: Float, right: Float): Boolean = left <= right
    override fun toString(): String = "$left <= $right"
}

/**
 * Checks whether this value is equal to the other value.
 */
infix fun Expression<Float>.eq(other: Expression<Float>): Expression<Boolean> = FloatEq(this, other)

/**
 * Checks whether this value is not equal to the other value.
 */
infix fun Expression<Float>.neq(other: Expression<Float>): Expression<Boolean> = FloatNeq(this, other)

/**
 * Checks whether this value is greater than the other value.
 */
infix fun Expression<Float>.greater(other: Expression<Float>): Expression<Boolean> = FloatGreater(this, other)

/**
 * Checks whether this value is greater than or equal to the other value.
 */
infix fun Expression<Float>.greaterEq(other: Expression<Float>): Expression<Boolean> = FloatGreaterEq(this, other)

/**
 * Checks whether this value is less than the other value.
 */
infix fun Expression<Float>.less(other: Expression<Float>): Expression<Boolean> = FloatLess(this, other)

/**
 * Checks whether this value is less than or equal to the other value.
 */
infix fun Expression<Float>.lessEq(other: Expression<Float>): Expression<Boolean> = FloatLessEq(this, other)
