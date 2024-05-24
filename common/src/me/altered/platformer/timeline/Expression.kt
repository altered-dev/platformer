package me.altered.platformer.timeline

import me.altered.platformer.util.KeyframeList
import kotlin.jvm.JvmInline

/**
 * A computable value to be used as a property of an object in a level.
 * TODO: serialize
 */
sealed interface Expression<T> {

    val value: T

    /**
     * An expression defined by a number that never changes.
     */
    @JvmInline
    value class Constant<T>(override val value: T) : Expression<T> {

        override fun toString() = value.toString()
    }

    /**
     * An expression defined by a lambda function. Unused in editor.
     */
    @JvmInline
    value class Function<T>(private val block: () -> T) : Expression<T> {

        override val value: T get() = block()
    }

    /**
     * An expression that transforms the results of two other expressions.
     */
    sealed class Combined<T> : Expression<T> {
        protected abstract val left: Expression<T>
        protected abstract val right: Expression<T>
    }

    /**
     * An expression defined by a set of keyframes and interpolated with a timeline.
     */
    sealed class Animated<T>(
        private val timeline: Timeline,
        private val keyframes: KeyframeList<T>,
    ) : Expression<T> {

        private var lastTime = Float.NaN
        private var lastValue: T? = null
        private var node = this.keyframes.find(timeline.time)

        override val value: T get() {
            if (timeline.time == lastTime) return lastValue as T
            lastTime = timeline.time
            node = node.shift(timeline.time)

            val (to, higher) = node
            if (to <= timeline.time) return higher.value.value.also { lastValue = it }
            val (from, lower) = node.prev ?: return higher.value.value.also { lastValue = it }

            val t = higher.easing.easeSafe(alerp(from, to, timeline.time))
            return animate(lower.value.value, higher.value.value, t).also { lastValue = it }
        }

        abstract fun animate(from: T, to: T, t: Float): T

        override fun toString() = keyframes.toString()
    }
}
