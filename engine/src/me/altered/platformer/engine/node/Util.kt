package me.altered.platformer.engine.node

import me.altered.platformer.engine.util.Logger

fun prettyString(node: Node) = buildString {
    prettyString(node, 0)
}

private fun StringBuilder.prettyString(node: Node, indent: Int) {
    appendLine("  ".repeat(indent) + "- " + node)
    node.children.forEach { prettyString(it, indent + 1) }
}

fun prettyPrint(node: Node) = Logger.d(node.toString(), prettyString(node))
