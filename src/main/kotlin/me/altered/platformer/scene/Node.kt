package me.altered.platformer.scene

import io.github.humbleui.skija.Canvas
import me.altered.platformer.glfw.input.InputEvent

abstract class Node(parent: ParentNode? = null) {

    open val name: String
        get() = "Node"

    internal var _parent: ParentNode? = parent
    open var parent: ParentNode?
        get() = _parent
        set(value) {
            _parent?._children?.remove(this)
            _parent = value
            value?._children?.add(this)
        }

    internal open fun _ready() {
        ready()
    }

    internal open fun _update(delta: Float) {
        update(delta)
    }

    internal open fun _physicsUpdate(delta: Float) {
        physicsUpdate(delta)
    }

    internal open fun _draw(canvas: Canvas) {
        canvas.save()
        draw(canvas)
        canvas.restore()
    }

    internal open fun _input(event: InputEvent) {
        input(event)
    }

    internal open fun _destroy() {
        destroy()
    }

    protected open fun ready() = Unit

    protected open fun update(delta: Float) = Unit

    protected open fun physicsUpdate(delta: Float) = Unit

    protected open fun draw(canvas: Canvas) = Unit

    protected open fun input(event: InputEvent) = Unit

    protected open fun destroy() = Unit

    override fun toString(): String = name
}
