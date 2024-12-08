package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import me.altered.platformer.engine.geometry.scale
import me.altered.platformer.level.data.toComposeBrush
import me.altered.platformer.level.objects.MutableRectangle
import me.altered.platformer.level.objects.Object

class MutableRectangleNode(
    override val obj: MutableRectangle,
    override var parent: MutableGroupNode? = null,
) : RectangleNode(obj, parent), MutableObjectNode {

    override var position: Offset
        get() = Offset(obj.x.staticValue, obj.y.staticValue)
        set(_) = Unit
    override var rotation: Float
        get() = obj.rotation.staticValue
        set(_) = Unit
    override var cornerRadius: Float
        get() = obj.cornerRadius.staticValue
        set(_) = Unit
    override var bounds: Rect
        get() = Object.baseBounds.scale(obj.width.staticValue, obj.height.staticValue)
        set(_) = Unit
    override var fill: List<Brush>
        get() = obj.fill.map { it.staticValue.toComposeBrush() }
        set(_) = Unit
    override var stroke: List<Brush>
        get() = obj.stroke.map { it.staticValue.toComposeBrush() }
        set(_) = Unit
    override var strokeWidth: Float
        get() = obj.strokeWidth.staticValue
        set(_) = Unit

    override fun toMutableObjectNode() = this
}
