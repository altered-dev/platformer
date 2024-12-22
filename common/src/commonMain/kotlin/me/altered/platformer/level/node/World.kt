package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawTransform
import androidx.compose.ui.util.lerp
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.CanvasTransformer
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
) : CanvasNode(name, parent), CanvasTransformer {

    override val children = listOfNotNull(grid, level, player)

    private var transform = Offset.Zero

    init {
        grid?.parent = this
        level?.parent = this
        player?.parent = this
    }

    override fun update(delta: Float) {
        val camera = level?.camera ?: return
        val player = player ?: return

        transform = Offset(
            x = lerp(transform.x, camera.position.x + player.position.x * camera.followPlayer.width, 4.0f * delta),
            y = lerp(transform.y, camera.position.y + player.position.y * camera.followPlayer.height, 4.0f * delta),
        )
    }

    override fun DrawScope.draw() {
        val bounds = size.toRect()
        val (left, top) = bounds.topLeft.screenToWorld(size) + transform
        val (right, bottom) = bounds.bottomRight.screenToWorld(size) + transform
        level?.let { drawRect(it.background, Offset(left, top), Size(right - left, bottom - top)) }
    }

    override fun DrawTransform.transform() {
        translate(-transform.x, -transform.y)
    }

    fun collide(position: Offset, radius: Float): List<CollisionInfo> {
        return level?.collide(position, radius).orEmpty()
    }
}
