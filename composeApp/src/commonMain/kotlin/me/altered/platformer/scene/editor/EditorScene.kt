package me.altered.platformer.scene.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import me.altered.platformer.level.World
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.expression.const
import me.altered.platformer.level.node.LevelNode

class EditorScene(
    level: MutableLevelState,
) : CanvasNode("editor") {

    private val world = +World(
        level = LevelNode(
            name = level.name,
            objects = level.objects,
        ),
        grid = Grid(),
    )

    fun setTime(time: Float) {
        world.level?.time = time
    }

    fun pan(offset: Offset) {
        world.position += offset
    }

    fun zoom(delta: Offset, cursorPos: Offset, size: Size) {
        if (delta == Offset.Zero) return
        val cursorDistance = cursorPos - (world.position + size.center)
        val scaleFrom = world.scale
        when {
            delta.x != 0.0f -> world.scale *= delta.x
            delta.y > 0.0f -> world.scale /= 1.0f + delta.y * 0.1f
            delta.y < 0.0f -> world.scale *= 1.0f - delta.y * 0.1f
        }
        world.position += cursorDistance * (1 - world.scale / scaleFrom)
    }

    fun hover(cursorPos: Offset, size: Size): ObjectNode<*>? {
        val cursorDistance = screenToWorld(cursorPos, size)
        return world.level?.objects?.findLast { cursorDistance in it.globalBounds }
    }

    fun select(rect: Rect, size: Size): List<ObjectNode<*>> {
        val rect = world.screenToWorld(rect, size)
        return world.level?.objects?.filter { it.globalBounds.overlaps(rect) }.orEmpty()
    }

    fun drag(nodes: List<ObjectNode<*>>, delta: Offset, size: Size) {
        val delta = delta / world.scale(size)
        nodes.forEach { node ->
            node.position += delta
            val obj = node.obj ?: return@forEach
            (obj.x as? AnimatedFloatState)?.staticValue = const(node.position.x)
            (obj.y as? AnimatedFloatState)?.staticValue = const(node.position.y)
        }
    }

    fun place(node: ObjectNode<*>) {
        world.level?.place(node)
    }

    fun remove(node: ObjectNode<*>) {
        world.level?.remove(node)
    }

    fun removeAll(nodes: List<ObjectNode<*>>) {
        world.level?.removeAll(nodes)
    }

    fun screenToWorld(vec: Offset, size: Size) = world.screenToWorld(vec, size)

    fun worldToScreen(vec: Offset, size: Size) = world.worldToScreen(vec, size)
}
