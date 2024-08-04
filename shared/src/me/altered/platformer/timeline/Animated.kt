package me.altered.platformer.timeline

import me.altered.koml.lerp
import me.altered.platformer.editor.Brush
import me.altered.platformer.editor.emptyBrush
import me.altered.platformer.editor.solid
import me.altered.platformer.util.KeyframeList
import org.jetbrains.skia.Color
import kotlin.jvm.JvmName

/**
 * An expression defined by a set of keyframes and interpolated with a timeline.
 */
abstract class Animated<T>(
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

    override fun toString() = buildString {
        append("animated(")
        var current = keyframes.first
        append(current)
        while (current.next != null) {
            append(", ")
            current = current.next!!
            append(current)
        }
        append(")")
    }
}

class AnimatedFloat(keyframes: KeyframeList<Float>) : Animated<Float>(keyframes) {
    override fun animate(from: Float, to: Float, t: Float): Float = lerp(from, to, t)
}

class AnimatedBrush(keyframes: KeyframeList<Brush>) : Animated<Brush>(keyframes) {
    override fun animate(from: Brush, to: Brush, t: Float): Brush {
        // TODO: support gradient animations
        if (from !is Brush.Solid || to !is Brush.Solid) return emptyBrush()
        return solid(Color.makeLerp(to.color, from.color, t))
    }
}

@JvmName("animatedFloat")
fun animated(
    vararg keyframes: Pair<Float, Keyframe<Float>>,
): Expression<Float> = AnimatedFloat(KeyframeList(keyframes))

@JvmName("animatedBrush")
fun animated(
    vararg keyframes: Pair<Float, Keyframe<Brush>>,
): Expression<Brush> = AnimatedBrush(KeyframeList(keyframes))
