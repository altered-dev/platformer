package me.altered.platformer.expression

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList

abstract class AnimatedState<T>(
    initial: T,
    keyframes: List<Keyframe<T>> = emptyList(),
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
