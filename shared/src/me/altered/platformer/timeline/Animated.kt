package me.altered.platformer.timeline

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import me.altered.koml.lerp
import me.altered.platformer.engine.graphics.Brush
import me.altered.platformer.engine.graphics.emptyBrush
import me.altered.platformer.engine.graphics.solid
import kotlin.jvm.JvmName

/**
 * An expression defined by a set of keyframes, which are being interpolated between.
 *
 * @param keyframes a list of keyframes, **sorted by time**.
 * [ArrayList] is used because it is known to have random access.
 */
@Serializable
sealed class Animated<T> : Expression<T> {

    abstract val keyframes: ArrayList<Keyframe<T>>

    @Transient
    private var lastTime = Float.NaN
    @Transient
    private var lastValue: T? = null
    @Transient
    private var index = 0

    abstract fun animate(from: T, to: T, t: Float): T

    @Suppress("UNCHECKED_CAST")
    final override fun eval(time: Float): T {
        if (time == lastTime) return lastValue as T
        lastTime = time

        while (index > 0 && keyframes[index - 1].time > time) {
            index--
        }
        while (index < keyframes.size - 1 && keyframes[index].time < time) {
            index++
        }

        return compute(time).also { lastValue = it }
    }

    private fun compute(time: Float): T {
        val (to, higher, easing) = keyframes[index]
        if (to <= time || index <= 0) return higher.eval(time)
        val (from, lower, _) = keyframes[index - 1]
        val t = easing.easeSafe(alerp(from, to, time))
        return animate(lower.eval(time), higher.eval(time), t)
    }
}

class AnimatedFloat(override val keyframes: ArrayList<Keyframe<Float>>) : Animated<Float>() {
    override fun animate(from: Float, to: Float, t: Float): Float = lerp(from, to, t)
}

class AnimatedBrush(override val keyframes: ArrayList<Keyframe<Brush>>) : Animated<Brush>() {
    override fun animate(from: Brush, to: Brush, t: Float): Brush {
        // TODO: support gradient animations
        if (from !is Brush.Solid || to !is Brush.Solid) return emptyBrush()
        return solid(from.color.lerp(to.color, t))
    }
}

@JvmName("animatedFloat")
fun animated(
    vararg keyframes: Keyframe<Float>,
): Expression<Float> = AnimatedFloat(arrayListOf(*keyframes))

@JvmName("animatedBrush")
fun animated(
    vararg keyframes: Keyframe<Brush>,
): Expression<Brush> = AnimatedBrush(arrayListOf(*keyframes))
