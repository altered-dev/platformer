package me.altered.platformer.scene

import io.github.humbleui.skija.Canvas
import me.altered.platformer.glfw.input.InputEvent

abstract class ParentNode(parent: ParentNode? = null) : Node(parent) {

    internal val _children = mutableSetOf<Node>()
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

    override fun _ready() {
        children.forEach { it._ready() }
        ready()
    }

    override fun _physicsUpdate(delta: Float) {
        physicsUpdate(delta)
        children.forEach { it._physicsUpdate(delta) }
    }

    override fun _draw(canvas: Canvas) {
        canvas.save()
        draw(canvas)
        canvas.restore()
        children.forEach { it._draw(canvas) }
    }

    override fun _input(event: InputEvent) {
        input(event)
        children.forEach { it._input(event) }
    }

    override fun _destroy() {
        children.forEach { it._destroy() }
        destroy()
    }
}
