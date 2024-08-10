package me.altered.platformer.level

import me.altered.platformer.level.node.ObjectNode

interface ObjectContainer {

    fun place(node: ObjectNode<*>)

    fun remove(node: ObjectNode<*>)
}
