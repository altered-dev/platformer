package me.altered.platformer.engine.node

import androidx.annotation.CallSuper
import androidx.compose.ui.input.key.KeyEvent
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

open class Node(
    val name: String = "Node",
    parent: Node? = null,
) {
    init {
        @Suppress("LeakingThis")
        parent?.addChild(this)
    }

    private var _parent: Node? = parent
    protected open val _children = mutableListOf<Node>()

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
    open val children: List<Node> by ::_children

    /**
     * The tree that contains the node.
     */
    var tree: SceneTree? = null
        get() = field ?: parent?.tree
        // hideous hack because no friend classes
        internal set

    /**
     * The topmost node in the tree.
     *
     * TODO: make it not be [SceneTree.root]
     */
    open val root: Node
        get() = parent?.root ?: this

    protected open val coroutineScope = CoroutineScope(CoroutineName(name))

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
        if (_children !== children) return false
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
        if (_children !== children) return false
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
        if (_children !== children) return false
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
        if (_children !== children) return false
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
     * Called at the start of the node's lifecycle, after all its [children] are ready.
     *
     * At this point it's safe to access [children]'s properties, but not [parent]'s.
     */
    @CallSuper
    open fun ready() = Unit

    /**
     * Called every frame, after [parent]. Can be used to update properties that affect graphical presentation.
     *
     * @param delta time since the last update
     */
    @CallSuper
    open fun update(delta: Float) = Unit

    /**
     * Called every fixed tick, after [parent]. Can be used to update properties that affect gameplay.
     *
     * @param delta time since the last update
     */
    @CallSuper
    open fun physicsUpdate(delta: Float) = Unit

    open fun onKeyEvent(event: KeyEvent) = false

    /**
     * Called when the node is about to be destroyed, usually via GC or when the game is closing.
     *
     * At this point it is NOT safe to use [parent], [children], [root], or [tree].
     */
    @CallSuper
    open fun destroy() {
        coroutineScope.cancel("Node is destroyed")
    }

    fun finalize() = destroy()
}
