package me.altered.platformer.ui

import io.github.humbleui.skija.Canvas
import io.github.humbleui.skija.PaintMode
import io.github.humbleui.types.Rect
import me.altered.platformer.glfw.input.InputEvent
import me.altered.platformer.node.Node
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.buildPaint
import kotlin.properties.Delegates

class Container(
    width: Size = expand,
    height: Size = expand,
    margin: Insets = none,
    horizontalAlignment: Alignment = start,
    verticalAlignment: Alignment = start,
) : Node() {

    var width by Delegates.observable(width, ::onChange)
    var height by Delegates.observable(height, ::onChange)
    var margin by Delegates.observable(margin, ::onChange)
    var horizontalAlignment by Delegates.observable(horizontalAlignment, ::onChange)
    var verticalAlignment by Delegates.observable(verticalAlignment, ::onChange)

    // TODO: create style class
    private val paint = buildPaint {
        isAntiAlias = true
        mode = PaintMode.STROKE
        strokeWidth = 1.0f
        color4f = Color.black
    }

    private var posX = 0.0f
    private var posY = 0.0f
    private var widthPx = 0.0f
    private var heightPx = 0.0f
    private var actualBounds = Rect.makeWH(0.0f, 0.0f)
    private var lastBounds: Rect? = null

    override fun ready() {
        onChange()
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(actualBounds.inflate(-0.5f), paint)
    }

    override fun input(event: InputEvent): Boolean {
        if (event !is InputEvent.WindowResize) return false
        measure(Rect.makeWH(event.width, event.height))
        return false
    }

    private fun onChange(p1: Any? = null, p2: Any? = null, p3: Any? = null) {
        val (width, height) = window?.size ?: return
        measure(Rect.makeWH(width.toFloat(), height.toFloat()), true)
        println(actualBounds)
    }

    private fun measure(bounds: Rect, force: Boolean = false) {
        if (bounds == lastBounds && !force) return
        lastBounds = bounds
        widthPx = when (val width = width) {
            is Size.Fixed -> width.value
            Size.Wrap -> 0.0f // TODO: implement wrapping
            Size.Expand -> bounds.width - margin.horizontal
        }
        heightPx = when (val height = height) {
            is Size.Fixed -> height.value
            Size.Wrap -> 0.0f // TODO: implement wrapping
            Size.Expand -> bounds.height - margin.vertical
        }
        posX = when (horizontalAlignment) {
            Alignment.START -> margin.left
            Alignment.CENTER -> (bounds.width - widthPx) * 0.5f
            Alignment.END -> bounds.right - margin.right - widthPx
        }
        posY = when (verticalAlignment) {
            Alignment.START -> margin.top
            Alignment.CENTER -> (bounds.height - heightPx) * 0.5f
            Alignment.END -> bounds.bottom - margin.bottom - heightPx
        }
        actualBounds = Rect.makeXYWH(posX, posY, widthPx, heightPx)
    }
}
