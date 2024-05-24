package me.altered.platformer

import me.altered.platformer.engine.input.InputEvent
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.MIDDLE
import me.altered.platformer.engine.input.Modifier
import me.altered.platformer.engine.input.MouseButton
import me.altered.platformer.engine.input.cursorMoved
import me.altered.platformer.engine.input.plus
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.input.scrolled
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.util.paint
import me.altered.platformer.ui.Button
import me.altered.platformer.ui.Text
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.color

class EditorScene : Node("editor") {

    private val originPaint = paint {
        isAntiAlias = true
        mode = PaintMode.STROKE
        color4f = Colors.black
    }

    private val gridPaint = paint {
        isAntiAlias = true
        mode = PaintMode.STROKE
        color4f = color(0x40000000)
    }

    // TODO: to camera class
    private var offsetX = Float.NaN
    private var offsetY = Float.NaN
    private var scale = 1.0f
    private var mousePosX = 0.0f
    private var mousePosY = 0.0f
    private var leftDragging = false
    private var middleDragging = false

    private var fps = 0.0f

    enum class Mode { CURSOR, RECTANGLE }
    private var mode = Mode.CURSOR
        set(value) {
            field = value
//            when (value) {
//                Mode.CURSOR -> window.setCursor(Cursor.ARROW)
//                Mode.RECTANGLE -> window.setCursor(Cursor.CROSSHAIR)
//            }
        }

    private val cursorButton = +Button(
        rect = Rect.makeXYWH(16.0f, 16.0f, 96.0f, 24.0f),
        name = "cursor",
        onClick = { mode = Mode.CURSOR },
    )

    private val rectangleButton = +Button(
        rect = Rect.makeXYWH(16.0f, 56.0f, 96.0f, 24.0f),
        name = "rectangle",
        onClick = { mode = Mode.RECTANGLE },
    )

    private val scaleText = +Text({ "scale: $scale" }, 16.0f, 128.0f, Colors.red)
    private val fpsText = +Text({ "fps: $fps" }, 16.0f, 144.0f, Colors.red)

    override fun input(event: InputEvent) {
        when {
            event pressed MouseButton.MIDDLE -> {
                middleDragging = true
                true
            }
            event released MouseButton.MIDDLE -> {
                middleDragging = false
                true
            }
            event.scrolled(Modifier.CONTROL) -> {
                when {
                    event.dy < 0 -> scale *= 1.1f
                    event.dy > 0 -> scale /= 1.1f
                }
                false
            }
            event.scrolled() -> {
                offsetX += event.dx
                offsetY -= event.dy
            }
            event pressed Modifier.CONTROL + Key.N0 -> {
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


    override fun update(delta: Float) {
        fps = 1.0f / delta
    }

    override fun draw(canvas: Canvas, bounds: Rect) {
        if (offsetX.isNaN()) {
            offsetX = bounds.width * 0.5f
            offsetY = bounds.height * 0.5f
        }

        val width = bounds.width / scale
        val height = bounds.height / scale

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
