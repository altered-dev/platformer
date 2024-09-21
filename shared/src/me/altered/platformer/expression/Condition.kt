package me.altered.platformer.expression

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("condition")
class Condition<T>(
    val condition: Expression<Boolean>,
    val positive: Expression<T>,
    val negative: Expression<T>,
) : Expression<T> {

    override fun eval(time: Float): T {
        return if (condition.eval(time)) positive.eval(time) else negative.eval(time)
    }

    override fun toString(): String {
        return "if ($condition) $positive else $negative"
    }
}

/**
 * Checks for the given [condition], evaluating [positive] if true or [negative] otherwise.
 */
fun <T> condition(
    condition: Expression<Boolean>,
    positive: Expression<T>,
    negative: Expression<T>,
): Expression<T> = Condition(condition, positive, negative)

// ---

@Serializable
@SerialName("not")
class BooleanNot(override val expression: Expression<Boolean>) : Unary<Boolean, Boolean>() {

    override fun transform(value: Boolean): Boolean = !value
    override fun toString(): String = "!$expression"
}

@Serializable
@SerialName("and")
class BooleanAnd(override val left: Expression<Boolean>, override val right: Expression<Boolean>) : Binary<Boolean, Boolean, Boolean>() {

    override fun transform(left: Boolean, right: Boolean): Boolean = left && right
    override fun toString(): String = "$left && $right"
}

@Serializable
@SerialName("or")
class BooleanOr(override val left: Expression<Boolean>, override val right: Expression<Boolean>) : Binary<Boolean, Boolean, Boolean>() {

    override fun transform(left: Boolean, right: Boolean): Boolean = left || right
    override fun toString(): String = "$left || $right"
}

// ---

operator fun Expression<Boolean>.not(): Expression<Boolean> = BooleanNot(this)

infix fun Expression<Boolean>.and(other: Expression<Boolean>): Expression<Boolean> = BooleanAnd(this, other)

infix fun Expression<Boolean>.or(other: Expression<Boolean>): Expression<Boolean> = BooleanOr(this, other)

