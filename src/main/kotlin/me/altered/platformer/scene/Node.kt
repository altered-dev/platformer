package me.altered.platformer.scene

import io.github.humbleui.skija.Canvas
import me.altered.platformer.glfw.input.InputEvent

abstract class Node(parent: Node? = null) {

    open val name: String
        get() = "Node"

    private var _parent: Node? = parent
    open var parent: Node?
        get() = _parent
        set(value) {
            _parent?._children?.remove(this)
            _parent = value
            value?._children?.add(this)
        }

    /*
     * FIXME: make a separate class for the ability to have children
     * (not all nodes should have children)
     */
    private val _children = mutableSetOf<Node>()
    open val children: Set<Node> by ::_children

    open fun addChild(child: Node) {
        if (child === this) return
        child._parent?._children?.remove(child)
        child._parent = this
        _children += child
    }

    open fun removeChild(child: Node) {
        if (child._parent !== this) return
        child._parent = null
        _children -= child
    }

    internal fun _ready() {
        children.forEach { it._ready() }
        ready()
    }

    internal fun _update(delta: Float) {
        update(delta)
        children.forEach { it._update(delta) }
    }

    internal fun _draw(canvas: Canvas) {
        canvas.save()
        draw(canvas)
        canvas.restore()
        children.forEach { it._draw(canvas) }
    }

    internal fun _input(event: InputEvent) {
        input(event)
        children.forEach { it.input(event) }
    }

    internal fun _destroy() {
        children.forEach { it.destroy() }
        destroy()
    }

    protected open fun ready() = Unit

    protected open fun update(delta: Float) = Unit

    protected open fun draw(canvas: Canvas) = Unit

    protected open fun input(event: InputEvent) = Unit

    protected open fun destroy() = Unit

    override fun toString(): String = name
}
