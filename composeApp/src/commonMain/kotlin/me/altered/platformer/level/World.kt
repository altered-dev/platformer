package me.altered.platformer.level

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.draw
import me.altered.platformer.level.data.CollisionInfo
import me.altered.platformer.level.objects.Object
import me.altered.platformer.level.objects.draw
import me.altered.platformer.level.objects.drawInEditor
import me.altered.platformer.level.player.Player
import me.altered.platformer.scene.editor.node.Grid

class World(
    name: String = "world",
    parent: Node? = null,
    val level: Level? = null,
    val player: Player? = null,
    val grid: Grid? = null,
) : CanvasNode(name, parent) {

    override val children = listOfNotNull(player)

    init {
        player?.parent = this
    }

    override fun DrawScope.draw() {
        grid?.draw(this)
        if (grid != null) level?.objects?.forEach {
            if (it is Object.Drawable) it.draw(this)
            if (it is Object.EditorDrawable) it.drawInEditor(this)
        }
        else level?.objects?.forEach {
            if (it is Object.Drawable) it.draw(this)
        }
    }

    fun collide(position: Offset, radius: Float): List<CollisionInfo> {
        return level?.objects
            ?.filterIsInstance<Object.HasCollision>()
            ?.flatMap { it.collide(position, radius) }
            .orEmpty()
    }
}
