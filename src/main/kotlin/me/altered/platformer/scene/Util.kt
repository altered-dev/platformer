package me.altered.platformer.scene

import kotlin.reflect.KProperty

fun prettyPrint(node: Node) = prettyPrint(node, 0)

private fun prettyPrint(node: Node, indent: Int) {
    println("  ".repeat(indent) + "- " + node)
    (node as? ParentNode)?.children?.forEach { prettyPrint(it, indent + 1) }
}

operator fun <T : Node> T.provideDelegate(thisRef: ParentNode, prop: KProperty<*>): T {
    parent = thisRef
    return this
}

operator fun <T : Node> T.getValue(thisRef: ParentNode, prop: KProperty<*>): T {
    if (parent !== thisRef) {
        parent = thisRef
    }
    return this
}
