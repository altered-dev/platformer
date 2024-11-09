package me.altered.platformer.level.node

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import me.altered.platformer.engine.geometry.scale
import me.altered.platformer.level.TimeContext
import me.altered.platformer.level.data.Rectangle
import me.altered.platformer.level.data.toComposeBrush
import me.altered.platformer.engine.node.Node

class RectangleNode(
    obj: Rectangle? = null,
    parent: Node? = null,
) : ObjectNode<Rectangle>(obj, parent), ObjectNode.Filled, ObjectNode.Stroked {

    override var fill: Brush = SolidColor(Color.Transparent)
    override var stroke: Brush = SolidColor(Color.Transparent)
    override var strokeWidth = 0.0f
    var cornerRadius = 0.0f

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
        cornerRadius = obj.cornerRadius.value
        fill = obj.fill.value.toComposeBrush()
        stroke = obj.stroke.value.toComposeBrush()
        strokeWidth = obj.strokeWidth.value
    }

    override fun collide(position: Offset, radius: Float, onCollision: (point: Offset) -> Unit) {
        rotated(position) { position ->
            val bounds = bounds.translate(this.position)
            val x = position.x.coerceIn(bounds.left, bounds.right)
            val y = position.y.coerceIn(bounds.top, bounds.bottom)
            val vec = Offset(x, y)
            if ((position - vec).getDistanceSquared() > (radius * radius)) {
                return
            }
            vec
        }.let(onCollision)
    }

    override fun DrawScope.draw() {
        drawRoundRect(fill, bounds.topLeft, bounds.size, CornerRadius(cornerRadius))
        drawRoundRect(stroke, bounds.topLeft, bounds.size, CornerRadius(cornerRadius), style = Stroke(strokeWidth))
    }
}
