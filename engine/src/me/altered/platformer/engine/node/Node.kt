package me.altered.platformer.engine.node

import me.altered.platformer.engine.input.InputEvent
import org.jetbrains.skia.Canvas

open class Node(
    open val name: String,
    parent: ParentNode? = null,
) {

    init {
        @Suppress("LeakingThis")
        parent?._children?.add(this)
    }

    internal var _parent: ParentNode? = parent
    var parent: ParentNode?
        get() = _parent
        set(value) {
            _parent?._children?.remove(this)
            _parent = value
            value?._children?.add(this)
        }

    open val root: ParentNode?
        get() = parent?.findRoot()

    open fun ready() = Unit

    open fun update(delta: Float) = Unit

    open fun physicsUpdate(delta: Float) = Unit

    open fun draw(canvas: Canvas, width: Float, height: Float) = Unit

    open fun input(event: InputEvent) = Unit

    open fun destroy() = Unit

    override fun toString() = "[${this::class.simpleName}] $name"
}
