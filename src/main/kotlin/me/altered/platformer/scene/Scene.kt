package me.altered.platformer.scene

import io.github.humbleui.skija.Canvas
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import me.altered.platformer.glfw.input.InputEvent

abstract class Scene(val name: String) {

    protected open val coroutineScope = CoroutineScope(CoroutineName("Scene [$name]"))

    open fun ready() = Unit

    open fun update(delta: Float) = Unit

    open fun draw(canvas: Canvas) = Unit

    open fun input(event: InputEvent) = Unit

    // TODO: cancel coroutine scope
    open fun destroy() = Unit

    protected fun finalize() {
        destroy()
        coroutineScope.cancel()
    }
}
