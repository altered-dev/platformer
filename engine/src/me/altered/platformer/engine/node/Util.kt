package me.altered.platformer.engine.node

fun prettyPrint(node: Node) = prettyPrint(node, 0)

private fun prettyPrint(node: Node, indent: Int) {
    println("  ".repeat(indent) + "- " + node)
    node.children.forEach { prettyPrint(it, indent + 1) }
}
