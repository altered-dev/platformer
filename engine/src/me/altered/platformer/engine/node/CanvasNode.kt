package me.altered.platformer.engine.node

import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.paint
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode
import kotlin.jvm.JvmStatic

open class CanvasNode(
    name: String = "CanvasNode",
    parent: Node? = null,
) : Node(name, parent) {

    open fun draw(canvas: Canvas) = Unit

    open fun debugDraw(canvas: Canvas) = Unit

    companion object {

        @JvmStatic
        protected val debugPaint = paint {
            isAntiAlias = true
            mode = PaintMode.STROKE
            strokeWidth = 2.0f
            color4f = Colors.red.withA(0.5f)
        }
    }
}