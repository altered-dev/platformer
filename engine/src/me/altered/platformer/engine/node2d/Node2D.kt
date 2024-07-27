package me.altered.platformer.engine.node2d

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.util.drawCrosshair
import org.jetbrains.skia.Canvas

open class Node2D(
    name: String = "Node2D",
    parent: Node? = null,
    val position: Vector2f = defaultPosition,
    var rotation: Float = defaultRotation,
    val scale: Vector2f = defaultScale,
    val skew: Vector2f = defaultSkew,
    // TODO: origin for rotation and scale
    // TODO: z index
) : CanvasNode(name, parent) {

    // TODO: rotation radians

    // TODO: optimize
    // also doesn't take into account a tree like this: Node2D > Node > Node2D
    // the last node will have globalPosition == position, although it's not always true
    open var globalPosition: Vector2fc
        get() = when (val parent = parent) {
            is Node2D -> position + parent.globalPosition
            else -> position
        }
        set(value) {
            position.set(when (val parent = parent) {
                is Node2D -> value - parent.globalPosition
                else -> value
            })
        }

    open var globalRotation: Float
        get() = when (val parent = parent) {
            is Node2D -> rotation + parent.globalRotation
            else -> rotation
        }
        set(value) {
            rotation = when (val parent = parent) {
                is Node2D -> value - parent.globalRotation
                else -> value
            }
        }

    open var globalScale: Vector2fc
        get() = when (val parent = parent) {
            is Node2D -> scale * parent.globalScale
            else -> scale
        }
        set(value) {
            scale.set(when (val parent = parent) {
                is Node2D -> value / parent.globalScale
                else -> value
            })
        }

    override fun debugDraw(canvas: Canvas) {
        canvas.drawCrosshair(0.0f, 0.0f, 5.0f, debugPaint)
    }

    companion object {

        val defaultPosition get() = Vector2f(0.0f, 0.0f)
        val defaultRotation get() = 0.0f
        val defaultScale get() = Vector2f(1.0f, 1.0f)
        val defaultSkew get() = Vector2f(0.0f, 0.0f)
    }
}
