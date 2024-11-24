package me.altered.platformer.expression

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.level.data.Brush
import kotlin.jvm.JvmInline

/**
 * An expression defined by a value that never changes.
 */
@JvmInline
@Serializable
@SerialName("const")
value class Constant<out T>(private val value: T) : Expression<T> {

    override fun eval(time: Float) = value

    override fun toString() = value.toString()
}

// Concrete classes

/**
 * An expression defined by a value that never changes.
 */
@JvmInline
@Serializable
@SerialName("float")
value class FloatConstant(private val value: Float) : Expression<Float> {

    override fun eval(time: Float) = value

    override fun toString() = value.toString()
}

/**
 * An expression defined by a value that never changes.
 */
@JvmInline
@Serializable
@SerialName("int")
value class IntConstant(private val value: Int) : Expression<Int> {

    override fun eval(time: Float) = value

    override fun toString() = value.toString()
}

/**
 * An expression defined by a value that never changes.
 */
@JvmInline
@Serializable
@SerialName("boolean")
value class BooleanConstant(private val value: Boolean) : Expression<Boolean> {

    override fun eval(time: Float) = value

    override fun toString() = value.toString()
}

/**
 * An expression defined by a value that never changes.
 */
@JvmInline
@Serializable
@SerialName("brush")
value class BrushConstant(private val value: Brush) : Expression<Brush> {

    override fun eval(time: Float) = value

    override fun toString() = value.toString()
}

/**
 * Creates an expression that is always resolved to [value].
 *
 * @param T the type of the expression.
 */
@Suppress("UNCHECKED_CAST")
fun <T> const(value: T): Expression<T> = when (value) {
    is Float -> const(value) as Expression<T>
    is Int -> const(value) as Expression<T>
    is Boolean -> const(value) as Expression<T>
    is Brush -> const(value) as Expression<T>
    else -> Constant(value)
}

// Factories

/**
 * Creates an expression that is always resolved to [value].
 */
fun const(value: Float): Expression<Float> = FloatConstant(value)

/**
 * Creates an expression that is always resolved to [value].
 */
fun const(value: Int): Expression<Int> = IntConstant(value)

/**
 * Creates an expression that is always resolved to [value].
 */
fun const(value: Boolean): Expression<Boolean> = BooleanConstant(value)

/**
 * Creates an expression that is always resolved to [value].
 */
fun const(value: Brush): Expression<Brush> = BrushConstant(value)
