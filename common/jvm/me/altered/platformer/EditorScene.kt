package me.altered.platformer

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect
import me.altered.platformer.glfw.input.Cursor
import me.altered.platformer.glfw.input.InputEvent
import me.altered.platformer.glfw.input.Key
import me.altered.platformer.glfw.input.KeyMod
import me.altered.platformer.glfw.input.MouseButton
import me.altered.platformer.glfw.input.cursorMoved
import me.altered.platformer.glfw.input.plus
import me.altered.platformer.glfw.input.pressed
import me.altered.platformer.glfw.input.released
import me.altered.platformer.glfw.input.scrolled
import me.altered.platformer.glfw.window.Window
import me.altered.platformer.node.ParentNode
import me.altered.platformer.node.getValue
import me.altered.platformer.node.provideDelegate
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.buildPaint
import me.altered.platformer.skija.color
import me.altered.platformer.ui.Button
import me.altered.platformer.ui.Text

class EditorScene(
    override val window: Window,
) : ParentNode() {

    private val originPaint = buildPaint {
        mode = PaintMode.STROKE
        color4f = Color.black
    }

    private val gridPaint = buildPaint {
        mode = PaintMode.STROKE
        color4f = color(0x40000000)
    }

    private val textPaint = buildPaint {
        color4f = Color.red
    }

    // TODO: to camera class
    private var offsetX = 0.0f
    private var offsetY = 0.0f
    private var scale = 1.0f
    private var mousePosX = 0.0f
    private var mousePosY = 0.0f
    private var leftDragging = false
    private var middleDragging = false

    enum class Mode { CURSOR, RECTANGLE }
    private var mode = Mode.CURSOR
        set(value) {
            field = value
            when (value) {
                Mode.CURSOR -> window.setCursor(Cursor.ARROW)
                Mode.RECTANGLE -> window.setCursor(Cursor.CROSSHAIR)
            }
        }

    private val cursorButton by Button(
        rect = Rect.makeXYWH(16.0f, 16.0f, 96.0f, 24.0f),
        name = "cursor",
        onClick = { mode = Mode.CURSOR },
    )

    private val rectangleButton by Button(
        rect = Rect.makeXYWH(16.0f, 56.0f, 96.0f, 24.0f),
        name = "rectangle",
        onClick = { mode = Mode.RECTANGLE },
    )

    override fun ready() {
        val (w, h) = window.size
        offsetX = w * 0.5f
        offsetY = h * 0.5f
    }

    override fun input(event: InputEvent): Boolean {
        return when {
            event pressed MouseButton.MIDDLE -> {
                middleDragging = true
                true
            }
            event released MouseButton.MIDDLE -> {
                middleDragging = false
                true
            }
            event.scrolled() -> {
                if (event.dy > 0) {
                    scale *= 1.1f
                } else {
                    scale /= 1.1f
                }
                false
            }
            event pressed KeyMod.CONTROL + Key.N0 -> {
                scale = 1.0f
                true
            }
            event.cursorMoved() -> {
                if (middleDragging) {
                    offsetX += (event.x - mousePosX) / scale
                    offsetY += (event.y - mousePosY) / scale
                }
                mousePosX = event.x
                mousePosY = event.y
                middleDragging
            }
            else -> false
        }
    }

    private var fps = 0.0f

    private val scaleText by Text("scale: $scale", 16.0f, 128.0f, Color.red)
    private val fpsText by Text("fps: $fps", 16.0f, 144.0f, Color.red)

    override fun update(delta: Float) {
        fps = 1.0f / delta
        scaleText.name = "scale: $scale"
        fpsText.name = "fps: $fps"
    }

    override fun draw(canvas: Canvas) {
        val (w, h) = window.size
        val width = w.toFloat()
        val height = h.toFloat()
//        val surface = canvas.surface ?: return
//        val width = surface.width.toFloat() / scale
//        val height = surface.height.toFloat() / scale

        canvas
            .translate(0.5f, 0.5f)
            .scale(scale, scale)
            .drawLine(0.0f, offsetY, width, offsetY, originPaint)
            .drawLine(offsetX, 0.0f, offsetX, height, originPaint)

        val step = 50.0f
        var dx = offsetX.mod(step)
        while (dx < width) {
            canvas.drawLine(dx, 0.0f, dx, height, gridPaint)
            dx += step
        }
        var dy = offsetY.mod(step)
        while (dy < height) {
            canvas.drawLine(0.0f, dy, width, dy, gridPaint)
            dy += step
        }
        canvas.translate(-0.5f, -0.5f)
    }
}
