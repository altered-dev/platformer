package me.altered.platformer.editor

import me.altered.koml.Vector2f
import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.graphics.contains
import me.altered.platformer.engine.graphics.offset
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.Left
import me.altered.platformer.engine.input.Middle
import me.altered.platformer.engine.input.Modifier
import me.altered.platformer.engine.input.MouseButton
import me.altered.platformer.engine.input.cursorMoved
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.input.scrolledWith
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.ui.Box
import me.altered.platformer.engine.ui.Text
import me.altered.platformer.engine.ui.end
import me.altered.platformer.engine.ui.padding
import me.altered.platformer.engine.ui.px
import me.altered.platformer.engine.util.andLog
import me.altered.platformer.expression.const
import me.altered.platformer.level.World
import me.altered.platformer.level.data.solid
import me.altered.platformer.level.rectangle
import org.jetbrains.skia.Shader

class EditorScene : Node("editor") {

    private val mousePos = Vector2f()
    private var leftDragging = false
    private var middleDragging = false
    private var timeDirection = 0.0f

    private val world = +World()

//    private val grid = world + Grid()
    private val selection = +Selection()

    private val topPanel = +TopPanel()
    private val inspector = +Box(
        horizontalAlignment = end,
        padding = padding(top = 48.0f),
    ) + Inspector()

    private val timeText = +Box(
        padding = padding(left = 16.0f, top = 64.0f),
    ) + Text(
        name = "time: 0.0",
        width = 256.px,
        fill = Shader.makeColor(Color.Black.value),
    )

    init {
        world.apply {
            rectangle(
                x = const(1.0f),
                y = const(1.0f),
                fill = const(solid(Color.Green)),
            )
            rectangle(
                x = const(5.0f),
                y = const(3.0f),
                fill = const(solid(Color.Green)),
            )
            rectangle(
                x = const(-3.0f),
                y = const(5.0f),
                fill = const(solid(Color.Green)),
            )
        }
    }

    override fun input(event: InputEvent) {
        when {
            // tools
            event.pressed(MouseButton.Left) -> when (topPanel.tool) {
                TopPanel.Tool.Cursor -> {
                    if (selection.objectRect?.contains(event.x, event.y) == true) {
                        leftDragging = true
                    } else {
                        selection.start = Vector2f(event.x, event.y)
                        selection.end = Vector2f(event.x, event.y)
                        trySelectObjects(event.modifier has Modifier.Shift)
                    }
                }
                else -> Unit
            }
            event.released(MouseButton.Left) -> {
                selection.start = null
                selection.end = null
                leftDragging = false
                // TODO: commit selection
            }

            // TODO: special extensions (possibly platform-dependent) for such shortcuts
            event pressed Key.Delete || event pressed Key.Backspace -> {
                world.removeChildren(selection.selectedObjects)
                selection.selectedObjects.clear()
            }

            // camera
            event pressed MouseButton.Middle -> middleDragging = true
            event released MouseButton.Middle -> middleDragging = false

            event.scrolledWith(Modifier.Control) -> {
                val oldPos = with(world) { mousePos.screenToWorld().andLog("old pos") }
                when {
                    event.dy < 0 -> world.size *= 1.1f
                    event.dy > 0 -> world.size /= 1.1f
                }
                val newPos = with(world) { mousePos.screenToWorld().andLog("new pos") }
                world.position += ((newPos - oldPos) * world.size).andLog("delta position")
            }
            event.cursorMoved() -> {
                if (middleDragging) {
                    world.position.x += event.x - mousePos.x
                    world.position.y += event.y - mousePos.y
                }
                if (leftDragging) {
                    val offset = with(world) { Vector2f(event.x, event.y).screenToWorld() - mousePos.screenToWorld() }
                    selection.move(offset)
                }
                if (selection.start != null) {
                    selection.end = Vector2f(event.x, event.y)
                    trySelectObjects()
                }
                mousePos.set(event.x, event.y)
            }

            event.pressed(Key.Left) -> selection.move(x = if (event.modifier has Modifier.Shift) -0.1f else -1.0f)
            event.pressed(Key.Right) -> selection.move(x = if (event.modifier has Modifier.Shift) 0.1f else 1.0f)
            event.pressed(Key.Up) -> selection.move(y = if (event.modifier has Modifier.Shift) -0.1f else -1.0f)
            event.pressed(Key.Down) -> selection.move(y = if (event.modifier has Modifier.Shift) 0.1f else 1.0f)

            // time
            event pressed Key.Q -> timeDirection -= 1.0f
            event released Key.Q -> timeDirection += 1.0f
            event pressed Key.E -> timeDirection += 1.0f
            event released Key.E -> timeDirection -= 1.0f
        }
    }

    override fun update(delta: Float) {
        world.time = (world.time + timeDirection * delta).coerceAtLeast(0.0f)
        timeText.name = "time: ${world.time}"
    }

    // TODO: a better name
    private fun trySelectObjects(additive: Boolean = false) {
        val rect = with(world) { selection.rect?.screenToWorld() } ?: return
        world.objects.forEach { obj ->
            if (rect.intersect(obj.bounds.offset(obj.globalPosition)) != null) {
                selection.selectedObjects += obj
            } else if (!additive) {
                selection.selectedObjects -= obj
            }
        }
        inspector.obj = selection.selectedObjects.singleOrNull()
    }

    companion object {

        private const val TAG = "EditorScene"
    }
}
