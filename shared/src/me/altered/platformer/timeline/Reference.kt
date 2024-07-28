package me.altered.platformer.timeline

import me.altered.platformer.objects.ObjectNode
import me.altered.platformer.objects.World

class ReferenceExpression<T>(
    world: World,
    name: String,
    property: ObjectNode.() -> Expression<T>,
): Expression<T> {

    private val property by lazy {
        world.objects.find { it.name == name }?.property()
            ?: error("Object with name '$name' not found in world '${world.name}'.")
    }

    override fun eval(time: Float): T {
        return property.eval(time)
    }
}

fun <T> World.reference(name: String, property: ObjectNode.() -> Expression<T>): Expression<T> {
    return ReferenceExpression(this, name, property)
}

fun <T> reference(name: String, property: ObjectNode.() -> Expression<T>): Expression<T> {
    return ReferenceExpression(TODO(), name, property)
}

fun x(node: ObjectNode) = node.xExpr

fun y(node: ObjectNode) = node.yExpr

fun rotation(node: ObjectNode) = node.rotationExpr