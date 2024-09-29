package me.altered.platformer.expression

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import me.altered.koml.lerp
import me.altered.platformer.level.data.Brush
import me.altered.platformer.level.data.emptyBrush
import me.altered.platformer.level.data.solid
import kotlin.jvm.JvmName

/**
 * An expression defined by a set of keyframes, which are being interpolated between.
 *
 * When the list of keyframes is immutable, the expression is considered stable.
 */
@Serializable
sealed class Animated<T> : Expression<T> {

    /**
     * The list of keyframes, **sorted by time**.
     */
    abstract val keyframes: List<Keyframe<T>>

    @Transient
    private var index = 0

    abstract fun animate(from: T, to: T, t: Float): T

    final override fun eval(time: Float): T {
        while (index > 0 && keyframes[index - 1].time > time) {
            index--
        }
        while (index < keyframes.size - 1 && keyframes[index].time < time) {
            index++
        }
        val (to, higher, easing) = keyframes[index]
        if (to <= time || index <= 0) return higher.eval(time)
        val (from, lower) = keyframes[index - 1]
        val t = easing.easeSafe(alerp(from, to, time))
        return animate(lower.eval(time), higher.eval(time), t)
    }
}

@Serializable
@SerialName("animatedFloat")
class AnimatedFloat(override val keyframes: List<Keyframe<Float>>) : Animated<Float>() {
    override fun animate(from: Float, to: Float, t: Float): Float = lerp(from, to, t)
}

@Serializable
@SerialName("animatedBrush")
class AnimatedBrush(override val keyframes: List<Keyframe<Brush>>) : Animated<Brush>() {
    override fun animate(from: Brush, to: Brush, t: Float): Brush {
        // TODO: support gradient animations
        if (from !is Brush.Solid || to !is Brush.Solid) return emptyBrush()
        return solid(from.color.lerp(to.color, t))
    }
}

@JvmName("animatedFloat")
fun animated(
    vararg keyframes: Keyframe<Float>,
): Expression<Float> = AnimatedFloat(keyframes.sortedBy { it.time })

@JvmName("animatedBrush")
fun animated(
    vararg keyframes: Keyframe<Brush>,
): Expression<Brush> = AnimatedBrush(keyframes.sortedBy { it.time })
