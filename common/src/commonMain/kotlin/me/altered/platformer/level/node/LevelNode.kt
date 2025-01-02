package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.Node
import me.altered.platformer.level.data.CollisionInfo
import me.altered.platformer.level.data.Level
import me.altered.platformer.level.data.draw
import me.altered.platformer.level.data.toComposeBrush

open class LevelNode(
    level: Level,
    parent: Node? = null,
) : CanvasNode(level.name, parent) {

    @Suppress("CanBePrimaryConstructorProperty")
    open val level = level

    open val camera = CameraNode(level.camera)
    open val objects = level.objects.map { it.toObjectNode() }

    open var background: Brush = SolidColor(Color.White)
        protected set

    override fun DrawScope.draw() {
        objects.forEach {
            if (it is ObjectNode.Drawable) it.draw(this)
        }
    }

    fun eval(time: Float) {
        camera.eval(time)
        background = level.background.eval(time).toComposeBrush()
        objects.forEach { it.eval(time) }
    }

    fun collide(position: Offset, radius: Float): List<CollisionInfo> {
        return objects.filterIsInstance<ObjectNode.HasCollision>()
            .flatMap { it.collide(position, radius) }
    }
}
