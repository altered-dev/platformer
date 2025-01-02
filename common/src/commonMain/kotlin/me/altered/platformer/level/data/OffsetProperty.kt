package me.altered.platformer.level.data

import androidx.compose.ui.geometry.Offset
import me.altered.platformer.action.effect.Effect
import me.altered.platformer.expression.Expression

/**
 * A property of an object node.
 */
sealed class Property<T : Any> {

    // this might result in a memory leak
    // a possible optimization is an accumulator value for completed effects
    private val effects = mutableListOf<Pair<Float, Effect<T>>>()

    fun eval(time: Float): T {
        var value = compute(time)
        effects.forEach { (start, effect) ->
            value += effect.produce(time - start)
        }
        return value
    }

    fun inject(effect: Effect<T>, time: Float) {
        effects += time to effect
    }

    /**
     * @return the evaluated expressions that are provided to the subclasses.
     */
    protected abstract fun compute(time: Float): T

    protected abstract operator fun T.plus(other: T): T
}

class FloatProperty(
    val expression: Expression<Float>,
) : Property<Float>() {

    override fun compute(time: Float) = expression.eval(time)

    override fun Float.plus(other: Float) = this + other
}

class OffsetProperty(
    val x: Expression<Float>,
    val y: Expression<Float>,
) : Property<Offset>() {

    override fun compute(time: Float) = Offset(x.eval(time), y.eval(time))

    override fun Offset.plus(other: Offset) = this + other
}
