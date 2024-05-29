package me.altered.platformer.`object`

import me.altered.platformer.editor.Grid
import me.altered.platformer.engine.node.Node2D

class World(
    time: Float = 0.0f,
    objects: List<ObjectNode> = emptyList(),
) : Node2D("world") {

    private val _objects = objects.toMutableList()
    val objects: List<ObjectNode> by ::_objects

    var time: Float = time
        set(value) {
            if (value == field) return
            field = value
            updateObjects(value)
        }

    init {
        updateObjects(time)
    }

    var showGrid: Boolean = false
        set(value) {
            if (value == field) return
            field = value
            if (value) {
                addChild(grid)
            } else {
                removeChild(grid)
            }
        }

    private val grid = Grid()

    private fun updateObjects(time: Float) {
        _objects.forEach { it.eval(time) }
    }

    fun addObject(obj: ObjectNode) {
        if (addChild(obj)) {
            _objects += obj
            obj.eval(time)
        }
        println(objects.joinToString("\n"))
    }

    fun removeObject(obj: ObjectNode) {
        if (removeChild(obj)) {
            _objects -= obj
        }
        println(objects.joinToString("\n"))
    }
}
