package me.altered.platformer.engine.ui

import me.altered.platformer.engine.graphics.Paint
import me.altered.platformer.engine.graphics.contains
import me.altered.platformer.engine.graphics.emptyRect
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Left
import me.altered.platformer.engine.input.MouseButton
import me.altered.platformer.engine.input.cursorMoved
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.Node
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect
import org.jetbrains.skia.Shader

abstract class UiNode(
    name: String = "UiNode",
    parent: Node? = null,
    var width: Size = expand(),
    var height: Size = expand(),
    fill: Shader = Shader.makeEmpty(),
    stroke: Shader = Shader.makeEmpty(),
    strokeWidth: Float = 0.0f,
) : CanvasNode(name, parent) {

    protected val fillPaint = Paint {
        mode = PaintMode.FILL
        shader = fill
    }
    protected val strokePaint = Paint {
        mode = PaintMode.STROKE
        shader = stroke
        this.strokeWidth = strokeWidth
    }

    open var fill by fillPaint::shader
    open var stroke by strokePaint::shader
    open var strokeWidth by strokePaint::strokeWidth

    protected val uiChildren = children.asSequence().filterIsInstance<UiNode>()

    var bounds: Rect = emptyRect()
    var globalBounds: Rect = emptyRect()
    var isMeasured = false

    var isHovered = false
        private set(value) {
            if (field == value) return
            field = value
            onHover(value)
        }

    var isPressed = false
        private set(value) {
            if (field == value) return
            field = value
            onClick(value)
        }

    abstract fun measure(width: Float, height: Float): Pair<Float, Float>

    protected open fun onHover(hovered: Boolean) = Unit

    protected open fun onClick(clicked: Boolean) = Unit

    protected fun measureSelf(size: Size, constraint: Float, padding: Float = 0.0f): Pair<Float, Float> {
        return when (size) {
            is Size.Fixed -> size.value to size.value - padding
            is Size.Expand -> {
                val measured = (constraint * size.fraction).coerceIn(size.min, size.max)
                measured to measured - padding
            }
            is Size.Wrap -> padding to constraint - padding
        }
    }

    protected fun wrapSelf(size: Size, max: Float, padding: Float = 0.0f): Float? {
        if (size !is Size.Wrap) return null
        return (max + padding).coerceIn(size.min, size.max)
    }

    override fun input(event: InputEvent) {
        when {
            event.cursorMoved() -> {
                isHovered = globalBounds.contains(event.x, event.y)
                if (!isHovered) {
                    isPressed = false
                }
            }
            event pressed MouseButton.Left -> {
                if (isHovered) {
                    isPressed = true
                }
            }
            event released MouseButton.Left -> {
                isPressed = false
            }
        }
    }
}
