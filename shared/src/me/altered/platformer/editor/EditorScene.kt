package me.altered.platformer.editor

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.platformer.MainScene
import me.altered.platformer.editor.action.Action
import me.altered.platformer.editor.action.CommittedAction
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.LEFT
import me.altered.platformer.engine.input.MIDDLE
import me.altered.platformer.engine.input.Modifier
import me.altered.platformer.engine.input.MouseButton
import me.altered.platformer.engine.input.cursorMoved
import me.altered.platformer.engine.input.plus
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.input.scrolled
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.Node2D
import me.altered.platformer.engine.node.SceneManager
import me.altered.platformer.engine.node.SceneManager.defer
import me.altered.platformer.engine.node.each
import me.altered.platformer.engine.node.prettyString
import me.altered.platformer.engine.node.ui.Text
import me.altered.platformer.engine.util.color
import me.altered.platformer.`object`.Ellipse
import me.altered.platformer.`object`.Rectangle
import me.altered.platformer.timeline.Timeline
import me.altered.platformer.timeline.const
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect

class EditorScene(
    private val timeline: Timeline = Timeline(),
    objects: Set<Node> = emptySet(),
) : Node("editor") {

    private var timeDirection = 0.0f
    private val mousePos = Vector2f()
    private var leftDragging = false
    private var middleDragging = false
    private var mode = Mode.POINTER

    private var fps = 0.0f

    private var lastStart: Vector2f? = null

    private val actions = ArrayDeque<CommittedAction<*>>()

    private val grid: Node2D
    private val _objects: Node2D
    private val world = +Node2D("world").apply {
        grid = +Grid()
        _objects = +Node2D("objects").apply {
            addChildren(objects)
        }
    }

    private val time = +Text("time: ${timeline.time}", margin = each(left = 16.0f, top = 72.0f))
    private val scaleText = +Text("scale: ${world.scale}", margin = each(left = 16.0f, top = 32.0f))
    private val fpsText = +Text("fps: $fps", margin = each(left = 16.0f, top = 56.0f))
    private val objectsText = +Text(prettyString(_objects), margin = each(left = 16.0f, top = 100.0f))

    override fun input(event: InputEvent) {
        when {
            event pressed Key.N1 -> {
                mode = Mode.POINTER
            }
            event pressed Key.N2 -> {
                mode = Mode.RECTANGLE
            }
            event pressed Key.N3 -> {
                mode = Mode.ELLIPSE
            }

            event pressed MouseButton.LEFT -> {
                lastStart = Vector2f(event.x, event.y)
                leftDragging = true
            }
            event released MouseButton.LEFT -> {
                val start = lastStart ?: return
                lastStart = null
                leftDragging = false
                val action = when (mode) {
                    Mode.POINTER -> return
                    Mode.RECTANGLE -> Action.DrawRectangle(start, Vector2f(event.x, event.y))
                    Mode.ELLIPSE -> Action.DrawEllipse(start, Vector2f(event.x, event.y))
                }
                commit(action)
            }
            event pressed Key.ESCAPE -> {
                lastStart = null
                leftDragging = false
            }
            event pressed MouseButton.MIDDLE -> {
                middleDragging = true
            }
            event released MouseButton.MIDDLE -> {
                middleDragging = false
            }
            event scrolled Modifier.CONTROL -> {
                val oldPos = mousePos.screenToWorld()
                when {
                    event.dy < 0 -> world.scale *= 1.1f
                    event.dy > 0 -> world.scale /= 1.1f
                }
                val newPos = mousePos.screenToWorld()
                world.position += (newPos - oldPos) * world.scale
            }
            event.scrolled() -> {
                world.position.x += event.dx
                world.position.y += event.dy
            }
            event pressed Modifier.CONTROL + Key.N0 -> {
                world.scale = Vector2f(1.0f, 1.0f)
            }
            event.cursorMoved() -> {
                if (middleDragging) {
                    world.position.x += event.x - mousePos.x
                    world.position.y += event.y - mousePos.y
                }
                mousePos.set(event.x, event.y)
                middleDragging
            }
            event pressed Modifier.CONTROL + Key.Z -> {
                if (actions.isNotEmpty()) {
                    cancel(actions.removeLast())
                }
            }
            event pressed Key.RIGHT -> timeDirection += 1
            event released Key.RIGHT -> timeDirection -= 1
            event pressed Key.LEFT -> timeDirection -= 1
            event released Key.LEFT -> timeDirection += 1
            event pressed Key.E -> {
                val children = _objects.children
                defer { SceneManager.scene = MainScene(timeline, children) }
            }
        }
    }

    override fun update(delta: Float) {
        fps = 1.0f / delta
        timeline.time += timeDirection * delta
        scaleText.text = "scale: ${world.scale}"
        time.text = "time: ${timeline.time}"
        fpsText.text = "fps: $fps"
    }

    override fun draw(canvas: Canvas, bounds: Rect) {
        if (leftDragging) {
            val start = lastStart ?: return
            when (mode) {
                Mode.POINTER -> Unit
                Mode.RECTANGLE -> {
                    canvas.drawRect(Rect.makeLTRB(start.x, start.y, mousePos.x, mousePos.y), debugPaint)
                }
                Mode.ELLIPSE -> {
                    canvas.drawOval(Rect.makeLTRB(start.x, start.y, mousePos.x, mousePos.y), debugPaint)
                }
            }
        }
    }

    private fun commit(action: Action<*>) {
        val product = produce(action)
        when (action) {
            is Action.DrawRectangle -> if (product is Rectangle) {
                _objects.addChild(product)
                objectsText.text = prettyString(_objects)
            }
            is Action.DrawEllipse -> if (product is Ellipse) {
                _objects.addChild(product)
                objectsText.text = prettyString(_objects)
            }
        }
        actions.addLast(CommittedAction(action, product))
    }

    private fun <T> produce(action: Action<T>): T {
        return when (action) {
            is Action.DrawRectangle -> {
                val start = action.start.screenToWorld()
                val end = action.end.screenToWorld()
                val size = end - start
                Rectangle(
                    timeline = timeline,
                    x = const(start.x + size.x * 0.5f),
                    y = const(start.y + size.y * 0.5f),
                    width = const(size.x),
                    height = const(size.y),
                    rotation = const(0.0f),
                    fill = const(color(0xFF333333)),
                ) as T
            }
            is Action.DrawEllipse -> {
                val start = action.start.screenToWorld()
                val end = action.end.screenToWorld()
                val size = end - start
                Ellipse(
                    timeline = timeline,
                    x = const(start.x + size.x * 0.5f),
                    y = const(start.y + size.y * 0.5f),
                    width = const(size.x),
                    height = const(size.y),
                    rotation = const(0.0f),
                    fill = const(color(0xFF333333)),
                ) as T
            }
        }
    }

    private fun cancel(action: CommittedAction<*>) {
        when (action.action) {
            is Action.DrawRectangle -> if (action.product is Rectangle) {
                _objects.removeChild(action.product)
                objectsText.text = prettyString(_objects)
            }
            is Action.DrawEllipse -> if (action.product is Ellipse) {
                _objects.removeChild(action.product)
                objectsText.text = prettyString(_objects)
            }
        }
    }

    private fun Vector2fc.screenToWorld(): Vector2fc {
        return (this - world.position) / world.scale
    }

    enum class Mode { POINTER, RECTANGLE, ELLIPSE }
}
