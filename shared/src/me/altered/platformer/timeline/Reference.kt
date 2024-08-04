package me.altered.platformer.timeline

import me.altered.platformer.objects.ObjectContainer
import me.altered.platformer.objects.ObjectNode

class Reference<T>(
    container: ObjectContainer,
    name: String,
    private val property: ObjectNode.() -> Expression<T>,
) : Expression<T> {

    val obj by lazy { container.find(name) ?: error("Object with name '$name' not found.") }

    override fun eval(time: Float): T = obj.property().eval(time)

    override fun toString(): String = "reference(${obj.name}, ${obj.property()})"
}

fun <T> ObjectContainer.reference(name: String, property: ObjectNode.() -> Expression<T>): Expression<T> {
    return Reference(this, name, property)
}

val x: ObjectNode.() -> Expression<Float> = { xExpr }

val y: ObjectNode.() -> Expression<Float> = { yExpr }

val rotation: ObjectNode.() -> Expression<Float> = { rotationExpr }
