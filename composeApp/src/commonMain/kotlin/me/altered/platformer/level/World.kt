package me.altered.platformer.level

import androidx.compose.ui.geometry.Offset
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.Node
import me.altered.platformer.level.data.CollisionInfo
import me.altered.platformer.level.node.LevelNode
import me.altered.platformer.level.player.Player
import me.altered.platformer.scene.editor.node.Grid

class World(
    name: String = "world",
    parent: Node? = null,
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

    fun collide(position: Offset, radius: Float): List<CollisionInfo> {
        return level?.collide(position, radius).orEmpty()
    }
}
