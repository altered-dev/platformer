package me.altered.platformer.objects

import me.altered.koml.Vector2fc
import me.altered.platformer.editor.Grid
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.util.observable
import org.jetbrains.skia.Rect

class World(
    time: Float = 0.0f,
) : Node2D("world"), ObjectContainer {

    private val _objects = mutableListOf<ObjectNode>()
    val objects: List<ObjectNode> by ::_objects

    var time by observable(time, ::updateObjects)
    private var initialized = false

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
        initialized = true
    }

    private fun updateObjects(time: Float) {
        _objects.forEach { it.eval(time) }
    }

    override fun place(obj: ObjectNode) {
        if (addChild(obj)) {
            _objects += obj
            if (initialized) {
                obj.eval(time)
            }
        }
    }

    override fun remove(obj: ObjectNode) {
        if (removeChild(obj)) {
            _objects -= obj
        }
    }

    override fun find(name: String): ObjectNode? {
        return _objects.find { it.name == name }
    }

    fun makeCollisions(position: Vector2fc, radius: Float, onCollision: (point: Vector2fc) -> Unit) {
        objects.forEach { obj ->
            obj.collide(position, radius, onCollision)
        }
    }
}
