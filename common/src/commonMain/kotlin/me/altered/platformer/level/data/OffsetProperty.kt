package me.altered.platformer.level.data

import androidx.compose.ui.geometry.Offset
import me.altered.platformer.action.Effect
import me.altered.platformer.expression.Expression
import me.altered.platformer.level.node.ObjectNode
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A property of an object node.
 */
sealed class Property<T : Any> : ReadOnlyProperty<ObjectNode, T> {

    private lateinit var value: T

    // this might result in a memory leak
    // a possible optimization is an accumulator value for completed effects
    private val effects = mutableListOf<Pair<Float, Effect<T>>>()

    fun eval(time: Float) {
        value = compute(time)
        effects.forEach { (start, effect) ->
            value += effect.produce(time - start)
        }
    }

    fun inject(effect: Effect<T>, time: Float) {
        effects += time to effect
    }

    /**
     * @return the evaluated expressions that are provided to the subclasses.
     */
    protected abstract fun compute(time: Float): T

    protected abstract operator fun T.plus(other: T): T

    final override fun getValue(thisRef: ObjectNode, property: KProperty<*>): T {
        return value
    }
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
