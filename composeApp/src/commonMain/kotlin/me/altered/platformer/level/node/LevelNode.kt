package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.Node
import me.altered.platformer.level.data.CollisionInfo
import me.altered.platformer.level.objects.draw

open class LevelNode(
    name: String,
    parent: Node? = null,
    open val objects: List<ObjectNode> = emptyList(),
) : CanvasNode(name, parent) {

    override fun DrawScope.draw() {
        objects.forEach {
            if (it is ObjectNode.Drawable) it.draw(this)
        }
    }

    fun eval(time: Float) {
        objects.forEach { it.eval(time) }
    }

    fun collide(position: Offset, radius: Float): List<CollisionInfo> {
        return objects.filterIsInstance<ObjectNode.HasCollision>()
            .flatMap { it.collide(position, radius) }
    }
}
