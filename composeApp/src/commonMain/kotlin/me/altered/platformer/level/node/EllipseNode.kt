package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import me.altered.platformer.geometry.normalize
import me.altered.platformer.geometry.scale
import me.altered.platformer.geometry.toComposeBrush
import me.altered.platformer.level.TimeContext
import me.altered.platformer.level.data.Ellipse
import me.altered.platformer.node.Node

class EllipseNode(
    obj: Ellipse? = null,
    parent: Node? = null,
) : ObjectNode<Ellipse>(obj, parent) {

    private var fillBrush: Brush = SolidColor(Color.Transparent)
    private var strokeBrush: Brush = SolidColor(Color.Transparent)
    private var strokeWidth = 0.0f

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
        fillBrush = obj.fill.value.toComposeBrush()
        strokeBrush = obj.stroke.value.toComposeBrush()
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
        drawOval(fillBrush, bounds.topLeft, bounds.size)
        drawOval(strokeBrush, bounds.topLeft, bounds.size, style = Stroke(strokeWidth))
    }
}
