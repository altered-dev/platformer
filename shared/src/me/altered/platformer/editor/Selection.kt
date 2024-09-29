package me.altered.platformer.editor

import me.altered.koml.Vector2fc
import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.graphics.Paint
import me.altered.platformer.engine.graphics.component1
import me.altered.platformer.engine.graphics.component2
import me.altered.platformer.engine.graphics.component3
import me.altered.platformer.engine.graphics.component4
import me.altered.platformer.engine.graphics.drawRect
import me.altered.platformer.engine.graphics.offset
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.level.node.ObjectNode
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect
import kotlin.math.max
import kotlin.math.min

class Selection : CanvasNode("selection") {

    var start: Vector2fc? = null
    var end: Vector2fc? = null

    val rect: Rect? get() {
        val start = start ?: return null
        val end = end ?: return null
        return Rect(
            left = min(start.x, end.x),
            top = min(start.y, end.y),
            right = max(start.x, end.x),
            bottom = max(start.y, end.y),
        )
    }

    var objectRect: Rect? = null
        private set

    val selectedObjects = mutableSetOf<ObjectNode<*>>()

    private val fillPaint = Paint {
        color = Color.Blue.copy(a = 25)
        mode = PaintMode.FILL
    }

    private val strokePaint = Paint {
        color = Color.Blue.copy(a = 255 / 2)
        mode = PaintMode.STROKE
        strokeWidth = 0.0f
    }

    fun move(x: Float = 0.0f, y: Float = 0.0f) {
        selectedObjects.forEach { obj -> obj.position.add(x, y) }
    }

    fun move(vec: Vector2fc) {
        selectedObjects.forEach { obj -> obj.position.add(vec) }
    }

    override fun update(delta: Float) {
        if (selectedObjects.isEmpty()) {
            objectRect = null
            return
        }
        var left = Float.MAX_VALUE
        var top = Float.MAX_VALUE
        var right = Float.MIN_VALUE
        var bottom = Float.MIN_VALUE
        selectedObjects.forEach {
            val (l, t, r, b) = with(it) { bounds.offset(globalPosition).worldToScreen() }
            left = min(left, l)
            top = min(top, t)
            right = max(right, r)
            bottom = max(bottom, b)
        }
        objectRect = Rect(left, top, right, bottom)
    }

    override fun draw(canvas: Canvas) {
        drawObjectSelection(canvas)
        drawCursorSelection(canvas)
    }

    private fun drawObjectSelection(canvas: Canvas) {
        val rect = objectRect ?: return
        canvas
            .drawRect(rect, strokePaint)
    }

    private fun drawCursorSelection(canvas: Canvas) {
        val rect = rect ?: return
        canvas
            .translate(0.5f, 0.5f)
            .drawRect(rect, fillPaint)
            .drawRect(rect, strokePaint)
    }
}
