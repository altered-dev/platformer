package me.altered.platformer.objects

interface ObjectContainer {

    fun place(obj: ObjectNode)

    fun remove(obj: ObjectNode)
}
