package me.altered.platformer.timeline

import java.util.*

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
    value class Constant<T>(override val value: T) : Expression<T>

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
        vararg keyframes: Pair<Float, Keyframe<T>>,
    ) : Expression<T> {

        init {
            require(keyframes.isNotEmpty()) { "An animated property must have at least one keyframe." }
        }

        private var lastTime: Float? = null
        private var lastValue: T? = null

        // TODO: unJVM the map
        private val keyframes = TreeMap(keyframes.toMap())

        override val value: T get() {
            if (timeline.time == lastTime) return lastValue as T
            lastTime = timeline.time
            require(keyframes.isNotEmpty()) { "An animated property must have at least one keyframe." }
            if (keyframes.size == 1) return keyframes.values.single().value.value.also { lastValue = it }

            // TODO: possibly optimise this to only traverse the tree once
            val (from, lower) = keyframes.floorEntry(timeline.time)
                ?: return keyframes.firstEntry().value.value.value.also { lastValue = it }
            val (to, higher) = keyframes.ceilingEntry(timeline.time)
                ?: return keyframes.lastEntry().value.value.value.also { lastValue = it }

            if (from == to) return lower.value.value

            return animate(lower.value.value, higher.value.value, alerp(from, to, timeline.time), higher.easing).also { lastValue = it }
        }

        abstract fun animate(from: T, to: T, time: Float, easing: Easing): T
    }
}
