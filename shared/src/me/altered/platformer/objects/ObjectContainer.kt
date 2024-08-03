package me.altered.platformer.objects

@ObjectsDsl
interface ObjectContainer {

    fun place(obj: ObjectNode)

    fun remove(obj: ObjectNode)

    fun find(name: String): ObjectNode?
}
