package me.altered.platformer.expression

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.util.lerp
import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.level.data.Brush
import me.altered.platformer.level.data.solid

sealed class AnimatedState<T>(
    initial: T,
    keyframes: List<Keyframe<T>> = emptyList(),
    val inspectorInfo: InspectorInfo,
) : Animated<T>() {

    private val _keyframes = keyframes.toMutableStateList()
    override val keyframes: List<Keyframe<T>> = _keyframes

    /**
     * The current value that is represented in the editor.
     */
    var staticValue by mutableStateOf(initial)

    /**
     * Creates an immutable copy of the animation state.
     */
    abstract fun toAnimated(): Animated<T>

    /**
     * Creates an immutable expression from the animated state.
     * May mangle the state, but the overall expression value remains unchanged.
     */
    fun toExpression(): Expression<T> = when (keyframes.size) {
        0 -> const(staticValue)
        1 -> keyframes.single().value
        else -> toAnimated()
    }

    override fun eval(time: Float): T {
        return when (keyframes.size) {
            0 -> staticValue
            else -> super.eval(time).also { staticValue = it }
        }
    }

    fun addFrame(keyframe: Keyframe<T>) {
        val index = _keyframes.indexOfFirst { it.time >= keyframe.time }
        if (index == -1) _keyframes += keyframe
        else _keyframes.add(index, keyframe)
    }

    fun removeFrame(keyframe: Keyframe<T>) {
        _keyframes -= keyframe
    }

    fun replaceFrame(old: Keyframe<T>, new: Keyframe<T>) {
        _keyframes -= old
        addFrame(new)
    }
}

// Concrete classes

class AnimatedFloatState(
    initial: Float,
    keyframes: List<Keyframe<Float>> = emptyList(),
    inspectorInfo: InspectorInfo = InspectorInfo.Unspecified,
) : AnimatedState<Float>(initial, keyframes, inspectorInfo) {

    constructor(initial: Float, inspectorInfo: InspectorInfo) : this(initial, emptyList(), inspectorInfo)

    override fun animate(from: Float, to: Float, t: Float): Float = lerp(from, to, t)

    override fun toAnimated() = AnimatedFloat(keyframes)
}

class AnimatedBrushState(
    initial: Brush,
    keyframes: List<Keyframe<Brush>> = emptyList(),
    inspectorInfo: InspectorInfo = InspectorInfo.Unspecified,
) : AnimatedState<Brush>(initial, keyframes, inspectorInfo) {

    constructor(initial: Brush, inspectorInfo: InspectorInfo) : this(initial, emptyList(), inspectorInfo)

    override fun animate(from: Brush, to: Brush, t: Float): Brush {
        // TODO: support gradient animations
        if (from !is Brush.Solid || to !is Brush.Solid) return solid(Color.Transparent)
        return solid(from.color.lerp(to.color, t))
    }

    override fun toAnimated() = AnimatedBrush(keyframes)
}

// Factories

fun Expression<Float>.toAnimatedFloatState(inspectorInfo: InspectorInfo = InspectorInfo.Unspecified) = when (this) {
    is AnimatedFloatState -> this
    is AnimatedFloat -> AnimatedFloatState(eval(0.0f), keyframes, inspectorInfo)
    is FloatConstant -> AnimatedFloatState(eval(0.0f), inspectorInfo = inspectorInfo)
    else -> AnimatedFloatState(eval(0.0f), listOf(Keyframe(0.0f, this)), inspectorInfo)
}

fun Expression<Brush>.toAnimatedBrushState(inspectorInfo: InspectorInfo = InspectorInfo.Unspecified) = when (this) {
    is AnimatedBrushState -> this
    is AnimatedBrush -> AnimatedBrushState(eval(0.0f), keyframes, inspectorInfo)
    is BrushConstant -> AnimatedBrushState(eval(0.0f), inspectorInfo = inspectorInfo)
    else -> AnimatedBrushState(eval(0.0f), listOf(Keyframe(0.0f, this)), inspectorInfo)
}
