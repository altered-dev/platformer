package me.altered.platformer.scene

import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty

fun <T : Node?> child(node: T): PropertyDelegateProvider<Node, ReadWriteProperty<Node, T>> = ChildNode(node)

fun prettyPrint(node: Node) = prettyPrint(node, 0)

private fun prettyPrint(node: Node, indent: Int) {
    println("  ".repeat(indent) + "- " + node.name)
    node.children.forEach { prettyPrint(it, indent + 1) }
}
