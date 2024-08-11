package me.altered.platformer.engine.ui

import me.altered.koml.Vector2f
import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.graphics.Paint
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.graphics.drawRect
import me.altered.platformer.engine.util.observable
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode

open class Box(
    name: String = "Box",
    parent: Node? = null,
    width: Size = expand,
    height: Size = expand,
    padding: Insets = none,
    anchor: Vector2f = Vector2f(0.0f, 0.0f),
    // TODO: replace with Shader
    background: Color = Color.Transparent,
    stroke: Color = Color.Transparent,
    strokeWidth: Float = 0.0f,
) : UiNode(name, parent, width, height, padding, anchor) {

    private val bgPaint = Paint {
        color = background
        mode = PaintMode.FILL
    }

    private val strokePaint = Paint {
        color = stroke
        this.strokeWidth = strokeWidth
        mode = PaintMode.STROKE
    }

    var background by observable(background) {
        bgPaint.color = it
    }

    var stroke by observable(stroke) {
        strokePaint.color = it
    }

    var strokeWidth by observable(strokeWidth) {
        strokePaint.strokeWidth = it
    }

    override fun draw(canvas: Canvas) {
        canvas
            .drawRect(bounds, bgPaint)
            .drawRect(bounds, strokePaint)
    }
}
