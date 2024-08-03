package me.altered.platformer.timeline

import me.altered.platformer.objects.ObjectContainer
import me.altered.platformer.objects.ObjectNode

fun <T> ObjectContainer.reference(name: String, property: ObjectNode.() -> Expression<T>): Expression<T> {
    val obj by lazy { find(name) ?: error("Object with name '$name' not found.") }
    return Expression { obj.property().eval(it) }
}

fun x(node: ObjectNode) = node.xExpr

fun y(node: ObjectNode) = node.yExpr

fun rotation(node: ObjectNode) = node.rotationExpr
