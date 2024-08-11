package me.altered.platformer.editor

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc
import me.altered.platformer.MainScene
import me.altered.platformer.editor.action.Action
import me.altered.platformer.editor.action.CommittedAction
import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.graphics.Paint
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.Left
import me.altered.platformer.engine.input.Middle
import me.altered.platformer.engine.input.Modifier
import me.altered.platformer.engine.input.MouseButton
import me.altered.platformer.engine.input.cursorMoved
import me.altered.platformer.engine.input.plus
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.input.scrolled
import me.altered.platformer.engine.input.scrolledWith
import me.altered.platformer.engine.node.prettyPrint
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.ui.Box
import me.altered.platformer.engine.ui.Text
import me.altered.platformer.engine.ui.all
import me.altered.platformer.engine.ui.each
import me.altered.platformer.engine.ui.expand
import me.altered.platformer.engine.ui.px
import me.altered.platformer.engine.graphics.contains
import me.altered.platformer.engine.graphics.drawOval
import me.altered.platformer.engine.graphics.drawRect
import me.altered.platformer.engine.graphics.offset
import me.altered.platformer.engine.graphics.transform
import me.altered.platformer.timeline.const
import me.altered.platformer.engine.util.logged
import me.altered.platformer.engine.util.observable
import me.altered.platformer.level.World
import me.altered.platformer.level.data.Ellipse
import me.altered.platformer.level.data.Rectangle
import me.altered.platformer.level.node.EllipseNode
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.level.node.RectangleNode
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect

class EditorScene : Node2D("editor") {

    private var timeDirection = 0.0f
    private val mousePos = Vector2f()
    private var leftDragging = false
    private var middleDragging = false
    private var lastStart: Vector2f? = null

    private val world = +World()
    private val grid = world + Grid(
        bounds = Rect.makeWH(
            w = window?.width?.toFloat() ?: 2000.0f,
            h = window?.height?.toFloat() ?: 2000.0f,
        ),
    )

    private var hovered: ObjectNode<*>? by logged(null)
    private var selected: ObjectNode<*>? by logged(null)

    private var fps: Float by observable(0.0f) {
        fpsText.text = "fps: $it"
    }
    private var tool: Tool by observable(Tool.Pointer) {
        toolText.text = "tool: $it"
    }

    private val actions = ArrayDeque<CommittedAction<*>>()
    private val undoneActions = ArrayDeque<Action<*>>()

    private val objPane = +Box(
        name = "objPane",
        width = 256.px,
        height = expand,
        padding = all(16.0f),
        background = Color(0xFF333333),
    )

    private val treeText = objPane + Text("", padding = each(left = 16.0f, top = 16.0f), color = Color.White)

    private val inspector = +Box(
        name = "inspector",
        width = 256.px,
        height = expand,
        padding = all(16.0f),
        anchor = Vector2f(1.0f, 0.0f),
        background = Color(0xFF333333),
    )
    private val scaleText = inspector + Text("scale: ${scale.x}", padding = each(top = 16.0f), color = Color.White)
    private val timeText = inspector + Text("time: ${world.time}", padding = each(top = 40.0f), color = Color.White)
    private val fpsText = inspector + Text("fps: $fps", padding = each(top = 64.0f), color = Color.White)
    private val toolText = inspector + Text("tool: $tool", padding = each(top = 88.0f), color = Color.White)

    override fun ready() {
        world.position.set(
            x = (window?.width ?: 0) * 0.25f,
            y = (window?.height ?: 0) * 0.75f,
        )
    }

    override fun input(event: InputEvent) {
        // TODO: refactor this mess
        when {
            // tools
            event pressed Key.N1 -> tool = Tool.Pointer
            event pressed Key.N2 -> tool = Tool.Rectangle
            event pressed Key.N3 -> tool = Tool.Ellipse

            // actions
            event pressed MouseButton.Left -> {
                lastStart = Vector2f(event.x, event.y)
                leftDragging = true
            }
            event released MouseButton.Left -> {
                val start = lastStart ?: return
                lastStart = null
                leftDragging = false
                val action = when (tool) {
                    Tool.Pointer -> Action.SelectObject(hovered, selected)
                    Tool.Rectangle -> Action.DrawRectangle(start.screenToWorld(), Vector2f(event.x, event.y).screenToWorld())
                    Tool.Ellipse -> Action.DrawEllipse(start.screenToWorld(), Vector2f(event.x, event.y).screenToWorld())
                }
                commit(action)
            }
            event pressed Key.Delete -> {
                val obj = selected ?: return
                commit(Action.DeleteObject(obj))
            }
            event pressed Key.Escape -> {
                lastStart = null
                leftDragging = false
            }

            // camera
            event pressed MouseButton.Middle -> middleDragging = true
            event released MouseButton.Middle -> middleDragging = false

            event scrolledWith Modifier.Control -> {
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
            event pressed Modifier.Control + Key.N0 -> {
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
            event pressed Modifier.Control + Modifier.Shift + Key.Z -> {
                if (undoneActions.isNotEmpty()) {
                    commit(undoneActions.removeLast())
                }
                prettyPrint(world)
            }
            event pressed Modifier.Control + Key.Z -> {
                if (actions.isNotEmpty()) {
                    undo(actions.removeLast())
                }
            }

            // time
            event pressed Key.Right -> timeDirection += 1
            event released Key.Right -> timeDirection -= 1
            event pressed Key.Left -> timeDirection -= 1
            event released Key.Left -> timeDirection += 1

            // navigation
            event pressed Key.E -> tree?.scene = MainScene()
        }
    }

    override fun update(delta: Float) {
        fps = 1.0f / delta
        world.time += timeDirection * delta
        scaleText.text = "scale: ${world.scale.x}"
        timeText.text = "time: ${world.time}"
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
                Tool.Pointer -> Unit
                Tool.Rectangle -> {
                    canvas.drawRect(Rect.makeLTRB(start.x, start.y, mousePos.x, mousePos.y), debugPaint)
                }
                Tool.Ellipse -> {
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
            is Action.DeleteObject -> {
                world.remove(action.obj)
                treeText.text = world.objects.joinToString("\n")
                selected = null
            }
            is Action.DrawRectangle -> if (product is RectangleNode) {
                world.place(product)
                treeText.text = world.objects.joinToString("\n")
            }
            is Action.DrawEllipse -> if (product is EllipseNode) {
                world.place(product)
                treeText.text = world.objects.joinToString("\n")
            }
        }
        actions.addLast(CommittedAction(action, product))
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> produce(action: Action<T>): T {
        return when (action) {
            is Action.SelectObject -> Unit as T
            is Action.DeleteObject -> Unit as T
            is Action.DrawRectangle -> {
                val start = action.start
                val end = action.end
                val size = end - start
                RectangleNode(
                    obj = Rectangle(
                        name = "rectangle",
                        x = const(start.x + size.x * 0.5f),
                        y = const(start.y + size.y * 0.5f),
                        rotation = const(0.0f),
                        width = const(size.x),
                        height = const(size.y),
                        cornerRadius = const(0.0f),
                        fill = const(solid(0xFF333333)),
                        stroke = const(emptyBrush()),
                        strokeWidth = const(0.0f),
                    )
                ) as T
            }
            is Action.DrawEllipse -> {
                val start = action.start
                val end = action.end
                val size = end - start
                EllipseNode(
                    obj = Ellipse(
                        name = "ellipse",
                        x = const(start.x + size.x * 0.5f),
                        y = const(start.y + size.y * 0.5f),
                        width = const(size.x),
                        height = const(size.y),
                        rotation = const(0.0f),
                        fill = const(solid(0xFF333333)),
                        stroke = const(emptyBrush()),
                        strokeWidth = const(0.0f),
                    )
                ) as T
            }
        }
    }

    private fun undo(action: CommittedAction<*>) {
        when (action.action) {
            is Action.SelectObject -> {
                selected = action.action.old
            }
            is Action.DeleteObject -> {
                world.place(action.action.obj)
                treeText.text = world.objects.joinToString("\n")
                selected = action.action.obj
            }
            is Action.DrawRectangle -> if (action.product is RectangleNode) {
                world.remove(action.product)
                treeText.text = world.objects.joinToString("\n")
            }
            is Action.DrawEllipse -> if (action.product is EllipseNode) {
                world.remove(action.product)
                treeText.text = world.objects.joinToString("\n")
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

    enum class Tool { Pointer, Rectangle, Ellipse }

    companion object {

        private val selectedPaint = Paint {
            isAntiAlias = true
            mode = PaintMode.STROKE
            strokeWidth = 2.0f
            color = Color.Blue.copy(a = 0x80)
        }
    }
}
