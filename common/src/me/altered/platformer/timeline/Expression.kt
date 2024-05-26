package me.altered.platformer.timeline

import me.altered.platformer.util.KeyframeList
import kotlin.jvm.JvmInline

/**
 * A computable value to be used as a property of an object in a level.
 * TODO: serialize
 */
fun interface Expression<T> {

    fun eval(time: Float): T

    /**
     * An expression defined by a number that never changes.
     */
    @JvmInline
    value class Constant<T>(private val value: T) : Expression<T> {

        override fun eval(time: Float): T = value

        override fun toString() = value.toString()
    }

    sealed interface Transformed<T> : Expression<T> {
        val original: Expression<T>
    }

    /**
     * An expression that transforms the results of two other expressions.
     */
    sealed interface Combined<T> : Expression<T> {
        val left: Expression<T>
        val right: Expression<T>
    }

    /**
     * An expression defined by a set of keyframes and interpolated with a timeline.
     */
    sealed class Animated<T>(
        private val keyframes: KeyframeList<T>,
    ) : Expression<T> {

        private var lastTime = Float.NaN
        private var lastValue: T? = null
        private var node = this.keyframes.first

        final override fun eval(time: Float): T {
            if (time == lastTime) return lastValue as T
            lastTime = time
            node = node.shift(time)
            return compute(time).also { lastValue = it }
        }

        private fun compute(time: Float): T {
            val (to, higher) = node
            if (to <= time) return higher.value.eval(time)
            val (from, lower) = node.prev ?: return higher.value.eval(time)

            val t = higher.easing.easeSafe(alerp(from, to, time))
            return animate(lower.value.eval(time), higher.value.eval(time), t)
        }

        abstract fun animate(from: T, to: T, t: Float): T

        override fun toString() = keyframes.toString()
    }
}
