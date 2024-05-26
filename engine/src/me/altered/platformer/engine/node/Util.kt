package me.altered.platformer.engine.node

fun prettyString(node: Node) = buildString {
    prettyString(node, 0)
}

private fun StringBuilder.prettyString(node: Node, indent: Int) {
    appendLine("  ".repeat(indent) + "- " + node)
    node.children.forEach { prettyString(it, indent + 1) }
}

fun prettyPrint(node: Node) = prettyPrint(node, 0)

private fun prettyPrint(node: Node, indent: Int) {
    println("  ".repeat(indent) + "- " + node)
    node.children.forEach { prettyPrint(it, indent + 1) }
}
