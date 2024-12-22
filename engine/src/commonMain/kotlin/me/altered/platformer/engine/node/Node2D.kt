package me.altered.platformer.engine.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawTransform
import me.altered.platformer.engine.geometry.div
import me.altered.platformer.engine.geometry.times

open class Node2D(
    name: String = "Node2D",
    parent: Node? = null,
    // TODO: probably move to mutable state
    open var position: Offset = Offset.Zero,
    open var rotation: Float = 0.0f,
    open var scale: Size = Size(1.0f, 1.0f),
) : CanvasNode(name, parent), CanvasTransformer {

    // TODO: rotation radians

    // TODO: optimize
    // also doesn't take into account a tree like this: Node2D > Node > Node2D
    // the last node will have globalPosition == position, although it's not always true
    open var globalPosition: Offset
        get() = when (val parent = parent) {
            is Node2D -> position + parent.globalPosition
            else -> position
        }
        set(value) {
            position = when (val parent = parent) {
                is Node2D -> value - parent.globalPosition
                else -> value
            }
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

    open var globalScale: Size
        get() = when (val parent = parent) {
            is Node2D -> scale * parent.globalScale
            else -> scale
        }
        set(value) {
            scale = when (val parent = parent) {
                is Node2D -> value / parent.globalScale
                else -> value
            }
        }

    override fun DrawTransform.transform() {
        translate(position.x, position.y)
        rotate(rotation, Offset.Zero)
        scale(scale.width, scale.height)
    }
}
