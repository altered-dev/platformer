package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import me.altered.platformer.engine.geometry.normalize
import me.altered.platformer.engine.geometry.scale
import me.altered.platformer.level.TimeContext
import me.altered.platformer.level.data.Ellipse
import me.altered.platformer.level.data.toComposeBrush
import me.altered.platformer.engine.node.Node

class EllipseNode(
    obj: Ellipse? = null,
    parent: Node? = null,
) : ObjectNode<Ellipse>(obj, parent), ObjectNode.Filled, ObjectNode.Stroked {

    override var fill: Brush = SolidColor(Color.Transparent)
    override var stroke: Brush = SolidColor(Color.Transparent)
    override var strokeWidth = 0.0f

    override fun TimeContext.eval() {
        val obj = obj ?: return
        position = Offset(
            x = obj.x.value,
            y = obj.y.value,
        )
        rotation = obj.rotation.value
        bounds = baseBounds.scale(
            sx = obj.width.value,
            sy = obj.height.value,
        )
        fill = obj.fill.value.toComposeBrush()
        stroke = obj.stroke.value.toComposeBrush()
        strokeWidth = obj.strokeWidth.value
    }

    override fun collide(position: Offset, radius: Float, onCollision: (point: Offset) -> Unit) {
        if (bounds.isEmpty) return
        rotated(position) {
            scaled(it) { position ->
                val rad = bounds.height * 0.5f
                val collision = position - this.position
                if (collision.getDistanceSquared() > (rad + radius) * (rad + radius)) {
                    return
                }
                this.position + collision.normalize(rad)
            }
        }.let(onCollision)
    }

    override fun DrawScope.draw() {
        drawOval(fill, bounds.topLeft, bounds.size)
        drawOval(stroke, bounds.topLeft, bounds.size, style = Stroke(strokeWidth))
    }
}
