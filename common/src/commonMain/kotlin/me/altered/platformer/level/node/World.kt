package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.Node
import me.altered.platformer.level.data.CollisionInfo
import me.altered.platformer.level.player.Player

class World(
    name: String = "world",
    parent: Node? = null,
    val screenToWorld: Offset.(Size) -> Offset = { this },
    val level: LevelNode? = null,
    val player: Player? = null,
    val grid: Grid? = null,
) : CanvasNode(name, parent) {

    override val children = listOfNotNull(grid, level, player)

    init {
        grid?.parent = this
        level?.parent = this
        player?.parent = this
    }

    override fun DrawScope.draw() {
        val bounds = size.toRect()
        val (left, top) = bounds.topLeft.screenToWorld(size)
        val (right, bottom) = bounds.bottomRight.screenToWorld(size)
        level?.let { drawRect(it.background, Offset(left, top), Size(right - left, bottom - top)) }
    }

    fun collide(position: Offset, radius: Float): List<CollisionInfo> {
        return level?.collide(position, radius).orEmpty()
    }
}
