package me.altered.platformer.timeline

class FloatEq(left: Expression<Float>, right: Expression<Float>) : Binary<Float, Float, Boolean>(left, right) {

    override fun transform(left: Float, right: Float): Boolean = left == right
    override fun toString(): String = "$left == $right"
}

class FloatNeq(left: Expression<Float>, right: Expression<Float>) : Binary<Float, Float, Boolean>(left, right) {

    override fun transform(left: Float, right: Float): Boolean = left != right
    override fun toString(): String = "$left != $right"
}

class FloatGreater(left: Expression<Float>, right: Expression<Float>) : Binary<Float, Float, Boolean>(left, right) {

    override fun transform(left: Float, right: Float): Boolean = left > right
    override fun toString(): String = "$left > $right"
}

class FloatGreaterEq(left: Expression<Float>, right: Expression<Float>) : Binary<Float, Float, Boolean>(left, right) {

    override fun transform(left: Float, right: Float): Boolean = left >= right
    override fun toString(): String = "$left >= $right"
}

class FloatLess(left: Expression<Float>, right: Expression<Float>) : Binary<Float, Float, Boolean>(left, right) {

    override fun transform(left: Float, right: Float): Boolean = left < right
    override fun toString(): String = "$left < $right"
}

class FloatLessEq(left: Expression<Float>, right: Expression<Float>) : Binary<Float, Float, Boolean>(left, right) {

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
