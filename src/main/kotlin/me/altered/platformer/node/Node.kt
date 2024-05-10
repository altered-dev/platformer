package me.altered.platformer.node

import io.github.humbleui.skija.Canvas
import me.altered.platformer.glfw.window.Window
import me.altered.platformer.glfw.input.InputEvent

abstract class Node(parent: ParentNode? = null) {

    open val name: String
        get() = this::class.simpleName ?: "<anonymous>"

    init {
        @Suppress("LeakingThis")
        parent?._children?.add(this)
    }

    internal var _parent: ParentNode? = parent
    open var parent: ParentNode?
        get() = _parent
        set(value) {
            _parent?._children?.remove(this)
            _parent = value
            value?._children?.add(this)
        }

    open val root: ParentNode?
        get() = parent?.findRoot()

    protected open val window: Window?
        get() = parent?.window

    /**
     * Called when the node enters the tree.
     *
     * A parent node is ready after all its children are ready.
     */
    open fun ready() = Unit

    /**
     * Called every frame.
     *
     * A parent node updates before its children.
     *
     * @param delta time in seconds since the last frame.
     */
    open fun update(delta: Float) = Unit

    /**
     * Called every physics tick.
     *
     * A parent node updates before its children.
     *
     * @param delta time in seconds since the last physics tick.
     */
    open fun physicsUpdate(delta: Float) = Unit

    /**
     * Called every time the node needs redrawing.
     *
     * A parent node is drawn before its children.
     *
     * @param canvas the canvas the node will be drawn onto.
     */
    open fun draw(canvas: Canvas) = Unit

    /**
     * Called every time an input event occurs.
     *
     * A parent node handles the events after its children.
     *
     * @param event the input event that occured.
     *
     * @return
     * - `true` if the event was handled and is not desired to be handled by other nodes.
     * - `false` if the event was handled or is desired to be handled by other nodes.
     */
    open fun input(event: InputEvent) = false

    /**
     * Called when the node exits the tree.
     *
     * A parent node is destroyed after its children.
     */
    open fun destroy() = Unit

    override fun toString(): String = "[${this::class.simpleName}] $name"
}
