package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import me.altered.platformer.engine.geometry.normalize
import me.altered.platformer.engine.geometry.scale
import me.altered.platformer.engine.node.Node
import me.altered.platformer.level.TimeContext
import me.altered.platformer.level.data.Brush
import me.altered.platformer.level.data.Ellipse
import me.altered.platformer.level.data.solid
import me.altered.platformer.level.data.toComposeBrush
import androidx.compose.ui.graphics.Brush as ComposeBrush

class EllipseNode(
    obj: Ellipse? = null,
    parent: Node? = null,
) : ObjectNode<Ellipse>(obj, parent), ObjectNode.Filled, ObjectNode.Stroked {

    override var fill: Brush = solid(0)
        set(value) {
            field = value
            _fill = value.toComposeBrush()
        }
    override var stroke: Brush = solid(0)
        set(value) {
            field = value
            _stroke = value.toComposeBrush()
        }
    override var strokeWidth = 0.0f

    private var _fill: ComposeBrush = SolidColor(Color.Unspecified)
    private var _stroke: ComposeBrush = SolidColor(Color.Unspecified)

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
        fill = obj.fill.value
        stroke = obj.stroke.value
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
        drawOval(_fill, bounds.topLeft, bounds.size)
        drawOval(_stroke, bounds.topLeft, bounds.size, style = Stroke(strokeWidth))
    }
}
