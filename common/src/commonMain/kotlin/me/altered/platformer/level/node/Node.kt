package me.altered.platformer.level.node

import me.altered.platformer.level.data.Ellipse
import me.altered.platformer.level.data.Group
import me.altered.platformer.level.data.MutableEllipse
import me.altered.platformer.level.data.MutableGroup
import me.altered.platformer.level.data.MutableRectangle
import me.altered.platformer.level.data.Object
import me.altered.platformer.level.data.Rectangle

fun Object.toObjectNode(parent: GroupNode? = null): ObjectNode = when (this) {
    is Ellipse -> EllipseNode(this, parent)
    is Group -> GroupNode(this, parent)
    is Rectangle -> RectangleNode(this, parent)
    is MutableEllipse -> EllipseNode(toObject(), parent)
    is MutableGroup -> GroupNode(toObject(), parent)
    is MutableRectangle -> RectangleNode(toObject(), parent)
}

fun Object.toMutableObjectNode(parent: MutableGroupNode? = null): MutableObjectNode = when (this) {
    is Ellipse -> MutableEllipseNode(toMutableObject(), parent)
    is Group -> MutableGroupNode(toMutableObject(), parent)
    is Rectangle -> MutableRectangleNode(toMutableObject(), parent)
    is MutableEllipse -> MutableEllipseNode(this, parent)
    is MutableGroup -> MutableGroupNode(this, parent)
    is MutableRectangle -> MutableRectangleNode(this, parent)
}
