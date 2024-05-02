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

    internal open fun _ready() = ready()

    internal open fun _update(delta: Float) = update(delta)

    internal open fun _physicsUpdate(delta: Float) = physicsUpdate(delta)

    internal open fun _draw(canvas: Canvas) {
        canvas.save()
        draw(canvas)
        canvas.restore()
    }

    internal open fun _input(event: InputEvent) = input(event)

    internal open fun _destroy() = destroy()


    /**
     * Called when the node enters the tree.
     *
     * A parent node is ready after all its children are ready.
     */
    protected open fun ready() = Unit

    /**
     * Called every frame.
     *
     * A parent node updates before its children.
     *
     * @param delta time in seconds since the last frame.
     */
    protected open fun update(delta: Float) = Unit

    /**
     * Called every physics tick.
     *
     * A parent node updates before its children.
     *
     * @param delta time in seconds since the last physics tick.
     */
    protected open fun physicsUpdate(delta: Float) = Unit

    /**
     * Called every time the node needs redrawing.
     *
     * A parent node is drawn before its children.
     *
     * @param canvas the canvas the node will be drawn onto.
     */
    protected open fun draw(canvas: Canvas) = Unit

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
    protected open fun input(event: InputEvent) = false

    /**
     * Called when the node exits the tree.
     *
     * A parent node is destroyed after its children.
     */
    protected open fun destroy() = Unit

    override fun toString(): String = name
}
