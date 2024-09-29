package me.altered.platformer.engine.node

import kotlinx.coroutines.flow.first
import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.platformer.engine.graphics.emptyRect
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.loop.SceneTree
import me.altered.platformer.engine.util.Logger
import me.altered.platformer.engine.util.d
import me.altered.platformer.engine.util.e
import me.altered.platformer.engine.util.i
import me.altered.platformer.engine.util.v
import me.altered.platformer.engine.util.w
import org.jetbrains.skia.Rect

open class Node(
    open var name: String = "Node",
    parent: Node? = null,
) {

    init {
        @Suppress("LeakingThis")
        parent?.addChild(this)
    }

    private var _parent: Node? = parent
    private val _children = mutableSetOf<Node>()

    /**
     * The direct parent of the node.
     * Setting a new parent will automatically place the node in its children.
     */
    var parent: Node?
        get() = _parent
        set(value) {
            _parent?.removeChild(this)
            _parent = value
            value?.addChild(this)
        }

    /**
     * The direct children of the node.
     * Manipulating the children is done via [addChild] and [removeChild],
     * which automatically set the parent of the child.
     */
    val children: Set<Node> by ::_children

    /**
     * The tree that contains the node.
     */
    var tree: SceneTree? = null
        get() = field ?: parent?.tree
        // ultra ugly hack because no friend classes
        internal set

    /**
     * The topmost node in the tree.
     *
     * TODO: make it not be [SceneTree.root]
     */
    open val root: Node
        get() = parent?.root ?: this

    /**
     * The closest [Viewport] node that is used to draw the node.
     */
    open val viewport: Viewport?
        get() = parent?.viewport

    /**
     * The window that contains the node.
     */
    open val window: Window?
        get() = parent?.window

    /**
     * Adds [child] to the node's [children]. Automatically sets the [parent] of [child] to the node.
     *
     * @param child the node to be added
     *
     * @return whether the operation was successful
     */
    fun addChild(child: Node): Boolean {
        // TODO: cyclic tree check
        if (child === this) return false
        child._parent?._children?.remove(child)
        child._parent = this
        return _children.add(child)
    }

    /**
     * Adds all [children] to the node.
     *
     * @param children the nodes to be added
     *
     * @return whether any nodes were added as children
     */
    fun addChildren(children: Iterable<Node>): Boolean {
        var result = false
        children.forEach { child ->
            if (addChild(child)) {
                result = true
            }
        }
        return result
    }

    /**
     * Removes [child] from the node's [children]. Automatically sets the [parent] of [child] to null.
     *
     * @param child the node to be removed
     *
     * @return whether the operation was successful
     */
    fun removeChild(child: Node): Boolean {
        if (child._parent !== this) return false
        child._parent = null
        return _children.remove(child)
    }

    /**
     * Removes all [children] from the node.
     *
     * @param children the nodes to be removed
     *
     * @return whether any nodes were removed
     */
    fun removeChildren(children: Iterable<Node>): Boolean {
        var result = false
        children.forEach { child ->
            if (removeChild(child)) {
                result = true
            }
        }
        return result
    }

    /**
     * Removes all [children] matching the [predicate].
     *
     * @param predicate the function that nodes are matched against
     *
     * @return whether any nodes were removed
     */
    inline fun removeChildren(predicate: (Node) -> Boolean): Boolean {
        var result = false
        children.forEach { child ->
            if (predicate(child) && removeChild(child)) {
                result = true
            }
        }
        return result
    }

    /**
     * A DSL function to conveniently add a child node in code.
     *
     * @receiver the node to be added
     *
     * @return the node itself
     */
    operator fun <N : Node> N.unaryPlus(): N {
        this@Node.addChild(this)
        return this
    }

    /**
     * A DSL function to conveniently add a child node in code.
     *
     * @receiver the node to be added
     *
     * @return the node itself
     */
    operator fun <N : Node> plus(node: N): N {
        addChild(node)
        return node
    }

    /**
     * TODO: not yet implemented into the lifecycle
     *
     * Called as soon as the node enters a tree.
     *
     * At this point it's safe to assume [tree] is not null.
     */
    open fun enterTree() = Unit

    /**
     * Called at the start of the node's lifecycle, after all its [children] are ready.
     *
     * At this point it's safe to access [children]'s properties, but not [parent]'s.
     * The only exception being [window], which is safe to access.
     */
    open fun ready() = Unit

    /**
     * Called every frame, after [parent]. Can be used to update properties that affect graphical presentation.
     *
     * @param delta time since the last update
     */
    open fun update(delta: Float) = Unit

    /**
     * Called every fixed tick, after [parent]. Can be used to update properties that affect gameplay.
     *
     * @param delta time since the last update
     */
    open fun physicsUpdate(delta: Float) = Unit

    /**
     * Called upon an input event being received, usually via the window framework's callbacks,
     * so it's not guaranteed to run on the same thread.
     *
     * @param event the input event. Use the `when` clause to figure out the event type
     */
    open fun input(event: InputEvent) = Unit

    /**
     * TODO: not yet implemented into the lifecycle
     *
     * Called when the node is about to leave a tree.
     *
     * At this point [tree] is still not null.
     */
    open fun exitTree() = Unit

    /**
     * Called when the node is about to be destroyed, usually via GC or when the game is closing.
     *
     * At this point it is NOT safe to use [parent], [children], [root], [tree], [viewport] or [window].
     */
    open fun destroy() = Unit

    fun finalize() = destroy()

    protected suspend fun waitNextFrame(): Float {
        return tree?.frameFlow?.first() ?: 0.0f
    }

    /**
     * Translates a point in screen coordinates to where this point is in viewport coordinates.
     */
    fun Vector2fc.screenToWorld(): Vector2fc {
        val viewport = viewport ?: return Vector2f()
        return (this - viewport.offset) / viewport.size
    }

    /**
     * Translates a point in viewport coordinates to where this point is in screen coordinates.
     */
    fun Vector2fc.worldToScreen(): Vector2fc {
        val viewport = viewport ?: return Vector2f()
        return this * viewport.size + viewport.offset
    }

    /**
     * Translates a rect in screen coordinates to where this rect is in viewport coordinates.
     */
    fun Rect.screenToWorld(): Rect {
        val viewport = viewport ?: return emptyRect()
        return offset(-viewport.offset.x, -viewport.offset.y).scale(1.0f / viewport.size)
    }

    /**
     * Translates a rect in viewport coordinates to where this rect is in screen coordinates.
     */
    fun Rect.worldToScreen(): Rect {
        val viewport = viewport ?: return emptyRect()
        return scale(viewport.size).offset(viewport.offset.x, viewport.offset.y)
    }

    fun Logger.v(message: Any?) = v(name, message)

    fun Logger.d(message: Any?) = d(name, message)

    fun Logger.i(message: Any?) = i(name, message)

    fun Logger.w(message: Any?) = w(name, message)

    fun Logger.e(message: Any?) = e(name, message)

    fun Logger.v(lazyMessage: () -> Any?) = v(name, lazyMessage)

    fun Logger.d(lazyMessage: () -> Any?) = d(name, lazyMessage)

    fun Logger.i(lazyMessage: () -> Any?) = i(name, lazyMessage)

    fun Logger.w(lazyMessage: () -> Any?) = w(name, lazyMessage)

    fun Logger.e(lazyMessage: () -> Any?) = e(name, lazyMessage)

    override fun toString() = "[${this::class.simpleName}] $name"
}
