package me.altered.platformer.level

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.draw
import me.altered.platformer.level.objects.Level
import me.altered.platformer.level.objects.Object
import me.altered.platformer.level.objects.draw
import me.altered.platformer.scene.editor.Grid

class World2(
    name: String = "world",
    parent: Node? = null,
    val level: Level? = null,
    val player: Player? = null,
    val grid: Grid? = null,
    var scale: Float = 1.0f,
    var position: Offset = Offset.Zero,
) : CanvasNode(name, parent) {

    override val children = emptyList<Node>()

    override fun DrawScope.draw() {
        grid?.draw(this)
        if (grid != null) level?.objects?.forEach {
            if (it is Object.EditorDrawable) it.draw(this)
        }
        else level?.objects?.forEach {
            if (it is Object.Drawable) it.draw(this)
        }
        player?.draw(this)
    }
}
