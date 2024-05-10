package me.altered.platformer.node

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

    override val root: ParentNode
        get() = findRoot()

    tailrec fun findRoot(): ParentNode {
        return parent?.findRoot() ?: this
    }
}
