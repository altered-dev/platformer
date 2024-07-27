package me.altered.platformer.objects

import me.altered.platformer.editor.Grid
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.util.observable
import org.jetbrains.skia.Rect

class World(
    time: Float = 0.0f,
    objects: List<ObjectNode> = emptyList(),
) : Node2D("world") {

    constructor(vararg objects: ObjectNode) : this(objects = objects.asList())

    private val _objects = objects.toMutableList()
    val objects: List<ObjectNode> by ::_objects

    var time by observable(time, ::updateObjects)

    init {
        addChildren(_objects)
    }

    var showGrid by observable(false) {
        if (it) {
            addChild(grid)
        } else {
            removeChild(grid)
        }
    }

    private lateinit var grid: Grid

    override fun ready() {
        grid = Grid(
            bounds = Rect.makeWH(
                w = window?.width?.toFloat() ?: 2000.0f,
                h = window?.height?.toFloat() ?: 2000.0f,
            )
        )
        if (showGrid) {
            addChild(grid)
        }
        updateObjects(time)
    }

    private fun updateObjects(time: Float) {
        _objects.forEach { it.eval(time) }
    }

    fun addObject(obj: ObjectNode) {
        if (addChild(obj)) {
            _objects += obj
            obj.eval(time)
        }
    }

    fun removeObject(obj: ObjectNode) {
        if (removeChild(obj)) {
            _objects -= obj
        }
    }
}
