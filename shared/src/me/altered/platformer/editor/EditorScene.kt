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
import me.altered.platformer.engine.input.scrolledWith
import me.altered.platformer.engine.node2d.Node2D
//import me.altered.platformer.engine.node.ui.Text
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.color
import me.altered.platformer.engine.util.contains
import me.altered.platformer.engine.util.offset
import me.altered.platformer.engine.util.paint
import me.altered.platformer.engine.util.transform
import me.altered.platformer.`object`.Ellipse
import me.altered.platformer.`object`.ObjectNode
import me.altered.platformer.`object`.Rectangle
import me.altered.platformer.`object`.World
import me.altered.platformer.timeline.const
import me.altered.platformer.engine.util.logged
import me.altered.platformer.engine.util.observable
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect

class EditorScene : Node2D("editor") {

    private var timeDirection = 0.0f
    private val mousePos = Vector2f()
    private var leftDragging = false
    private var middleDragging = false
    private var lastStart: Vector2f? = null

    private val world = +World().apply {
        showGrid = true
    }

    private var hovered: ObjectNode? by logged(null)
    private var selected: ObjectNode? by logged(null)

    private var fps: Float by observable(0.0f) {
//        fpsText.text = "fps: $it"
    }
    private var tool: Tool by observable(Tool.POINTER) {
//        toolText.text = "tool: $it"
    }

    private val actions = ArrayDeque<CommittedAction<*>>()
    private val undoneActions = ArrayDeque<Action<*>>()

//    private val fpsText = +Text("fps: $fps", margin = each(left = 16.0f, top = 16.0f))
//    private val toolText = +Text("tool: $tool", margin = each(left = 16.0f, top = 32.0f))
//    private val scaleText = +Text("scale: ${world.scale}", margin = each(left = 16.0f, top = 48.0f))
//    private val timeText = +Text("time: ${world.time}", margin = each(left = 16.0f, top = 64.0f))

    override fun input(event: InputEvent) {
        // TODO: refactor this mess
        when {
            // tools
            event pressed Key.N1 -> tool = Tool.POINTER
            event pressed Key.N2 -> tool = Tool.RECTANGLE
            event pressed Key.N3 -> tool = Tool.ELLIPSE

            // actions
            event pressed MouseButton.LEFT -> {
                lastStart = Vector2f(event.x, event.y)
                leftDragging = true
            }
            event released MouseButton.LEFT -> {
                val start = lastStart ?: return
                lastStart = null
                leftDragging = false
                val action = when (tool) {
                    Tool.POINTER -> Action.SelectObject(hovered, selected)
                    Tool.RECTANGLE -> Action.DrawRectangle(start, Vector2f(event.x, event.y))
                    Tool.ELLIPSE -> Action.DrawEllipse(start, Vector2f(event.x, event.y))
                }
                commit(action)
            }
            event pressed Key.ESCAPE -> {
                lastStart = null
                leftDragging = false
            }

            // camera
            event pressed MouseButton.MIDDLE -> middleDragging = true
            event released MouseButton.MIDDLE -> middleDragging = false

            event scrolledWith Modifier.CONTROL -> {
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
                world.scale.set(1.0f, 1.0f)
            }

            // objects
            event.cursorMoved() -> {
                if (middleDragging) {
                    world.position.x += event.x - mousePos.x
                    world.position.y += event.y - mousePos.y
                }
                mousePos.set(event.x, event.y)
                hovered = world.objects.findLast { mousePos.screenToWorld() in it.bounds.offset(it.position) }
                middleDragging
            }
            event pressed Modifier.CONTROL + Modifier.SHIFT + Key.Z -> {
                if (undoneActions.isNotEmpty()) {
                    commit(undoneActions.removeLast())
                }
            }
            event pressed Modifier.CONTROL + Key.Z -> {
                if (actions.isNotEmpty()) {
                    undo(actions.removeLast())
                }
            }

            // time
            event pressed Key.RIGHT -> timeDirection += 1
            event released Key.RIGHT -> timeDirection -= 1
            event pressed Key.LEFT -> timeDirection -= 1
            event released Key.LEFT -> timeDirection += 1

            // navigation
            event pressed Key.E -> tree?.currentScene = MainScene()
        }
    }

    override fun update(delta: Float) {
        fps = 1.0f / delta
        world.time += timeDirection * delta
//        scaleText.text = "scale: ${world.scale}"
//        timeText.text = "time: ${world.time}"
//        fpsText.text = "fps: $fps"
    }

    override fun draw(canvas: Canvas) {
        hovered?.let { hovered ->
            canvas.save()
            canvas
                .transform(world)
                .transform(hovered)
            canvas.drawRect(hovered.bounds, debugPaint)
            canvas.restore()
        }
        selected?.let { selected ->
            canvas.save()
            canvas
                .transform(world)
                .transform(selected)
            canvas.drawRect(selected.bounds, selectedPaint)
            canvas.restore()
        }

        if (leftDragging) {
            val start = lastStart ?: return
            when (tool) {
                Tool.POINTER -> Unit
                Tool.RECTANGLE -> {
                    canvas.drawRect(Rect.makeLTRB(start.x, start.y, mousePos.x, mousePos.y), debugPaint)
                }
                Tool.ELLIPSE -> {
                    canvas.drawOval(Rect.makeLTRB(start.x, start.y, mousePos.x, mousePos.y), debugPaint)
                }
            }
        }
    }

    private fun commit(action: Action<*>) {
        val product = produce(action)
        when (action) {
            is Action.SelectObject -> {
                selected = action.new
            }
            is Action.DrawRectangle -> if (product is Rectangle) {
                world.addObject(product)
            }
            is Action.DrawEllipse -> if (product is Ellipse) {
                world.addObject(product)
            }
        }
        actions.addLast(CommittedAction(action, product))
    }

    private fun <T> produce(action: Action<T>): T {
        return when (action) {
            is Action.SelectObject -> Unit as T
            is Action.DrawRectangle -> {
                val start = action.start.screenToWorld()
                val end = action.end.screenToWorld()
                val size = end - start
                Rectangle(
                    xExpr = const(start.x + size.x * 0.5f),
                    yExpr = const(start.y + size.y * 0.5f),
                    rotationExpr = const(0.0f),
                    widthExpr = const(size.x),
                    heightExpr = const(size.y),
                    fillExpr = const(color(0xFF333333)),
                ) as T
            }
            is Action.DrawEllipse -> {
                val start = action.start.screenToWorld()
                val end = action.end.screenToWorld()
                val size = end - start
                Ellipse(
                    xExpr = const(start.x + size.x * 0.5f),
                    yExpr = const(start.y + size.y * 0.5f),
                    widthExpr = const(size.x),
                    heightExpr = const(size.y),
                    rotationExpr = const(0.0f),
                    fillExpr = const(color(0xFF333333)),
                ) as T
            }
        }
    }

    private fun undo(action: CommittedAction<*>) {
        when (action.action) {
            is Action.SelectObject -> {
                selected = action.action.old
            }
            is Action.DrawRectangle -> if (action.product is Rectangle) {
                world.removeObject(action.product)
            }
            is Action.DrawEllipse -> if (action.product is Ellipse) {
                world.removeObject(action.product)
            }
        }
        undoneActions.addLast(action.action)
    }

    private fun Vector2fc.screenToWorld(): Vector2fc {
        return (this - world.position) / world.scale
    }

    private fun Vector2fc.worldToScreen(): Vector2fc {
        return this * world.scale + world.position
    }

    enum class Tool { POINTER, RECTANGLE, ELLIPSE }

    companion object {

        private val selectedPaint = paint {
            isAntiAlias = true
            mode = PaintMode.STROKE
            strokeWidth = 2.0f
            color4f = Colors.blue.withA(0.5f)
        }
    }
}
