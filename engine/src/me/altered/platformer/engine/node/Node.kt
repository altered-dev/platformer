package me.altered.platformer.engine.node

import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.loop.SceneTree

open class Node(
    open var name: String = "Node",
    parent: Node? = null,
) {

    init {
        @Suppress("LeakingThis")
        parent?.addChild(this)
    }

    private var _parent: Node? = parent
    var parent: Node?
        get() = _parent
        set(value) {
            _parent?.removeChild(this)
            _parent = value
            value?.addChild(this)
        }

    private val _children = mutableSetOf<Node>()
    val children: Set<Node> by ::_children

    var tree: SceneTree? = null
        get() = field ?: parent?.tree
        // ultra ugly hack because no friend classes
        internal set

    open val root: Node
        get() = parent?.root ?: this

    open val viewport: Viewport?
        get() = parent?.viewport

    open val window: Window?
        get() = parent?.window

    fun addChild(child: Node): Boolean {
        // TODO: cyclic tree check
        if (child === this) return false
        child._parent?._children?.remove(child)
        child._parent = this
        return _children.add(child)
    }

    fun addChildren(children: Iterable<Node>): Boolean {
        var result = false
        children.forEach { child ->
            if (addChild(child)) {
                result = true
            }
        }
        return result
    }

    fun removeChild(child: Node): Boolean {
        if (child._parent !== this) return false
        child._parent = null
        return _children.remove(child)
    }

    fun removeChildren(children: Iterable<Node>): Boolean {
        var result = false
        children.forEach { child ->
            if (removeChild(child)) {
                result = true
            }
        }
        return result
    }

    inline fun removeChildren(predicate: (Node) -> Boolean) {
        children.forEach { child ->
            if (predicate(child)) removeChild(child)
        }
    }

    operator fun <N : Node> N.unaryPlus(): N {
        this@Node.addChild(this)
        return this
    }

    open fun enterTree() = Unit

    open fun ready() = Unit

    open fun update(delta: Float) = Unit

    open fun physicsUpdate(delta: Float) = Unit

    open fun input(event: InputEvent) = Unit

    open fun exitTree() = Unit

    open fun destroy() = Unit

    override fun toString() = "[${this::class.simpleName}] $name"
}
