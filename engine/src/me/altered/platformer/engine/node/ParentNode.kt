package me.altered.platformer.engine.node

abstract class ParentNode(
    name: String,
    parent: ParentNode? = null,
) : Node(name, parent) {

    internal val _children = mutableSetOf<Node>()
    val children: Set<Node> by ::_children

    fun addChild(child: Node) {
        if (child === this) return
        child._parent?._children?.remove(child)
        child._parent = this
        _children += child
    }

    fun removeChild(child: Node) {
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
