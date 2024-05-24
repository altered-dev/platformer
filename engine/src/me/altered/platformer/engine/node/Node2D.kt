package me.altered.platformer.engine.node

import me.altered.koml.Vector2f
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect

open class Node2D(
    name: String,
    parent: Node? = null,
    open var position: Vector2f = Vector2f(0.0f, 0.0f),
    open var rotation: Float = 0.0f,
    open var scale: Vector2f = Vector2f(1.0f, 1.0f),
    // TODO: origin
) : Node(name, parent) {

    final override fun debugDraw(canvas: Canvas, bounds: Rect) {
        canvas
            .drawLine(0.0f, -5.0f, 0.0f, 5.0f, debugPaint)
            .drawLine(-5.0f, 0.0f, 5.0f, 0.0f, debugPaint)
    }
}
