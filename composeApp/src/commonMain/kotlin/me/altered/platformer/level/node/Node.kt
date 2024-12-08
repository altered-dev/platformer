package me.altered.platformer.level.node

import me.altered.platformer.level.objects.Ellipse
import me.altered.platformer.level.objects.Group
import me.altered.platformer.level.objects.MutableEllipse
import me.altered.platformer.level.objects.MutableGroup
import me.altered.platformer.level.objects.MutableRectangle
import me.altered.platformer.level.objects.Object
import me.altered.platformer.level.objects.Rectangle

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
