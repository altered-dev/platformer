package me.altered.platformer.`object`

import me.altered.platformer.editor.Grid
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.util.observable
import org.jetbrains.skia.Rect

class World(
    time: Float = 0.0f,
    objects: List<ObjectNode> = emptyList(),
) : Node2D("world") {

    private val _objects = objects.toMutableList()
    val objects: List<ObjectNode> by ::_objects

    var time by observable(time, ::updateObjects)

    init {
        updateObjects(time)
    }

    var showGrid by observable(false) {
        if (it) {
            addChild(grid)
        } else {
            removeChild(grid)
        }
    }

    private val grid = Grid(Rect.makeWH(2000.0f, 2000.0f))

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