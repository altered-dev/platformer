package me.altered.platformer.editor.action

import me.altered.koml.Vector2fc
import me.altered.platformer.level.node.EllipseNode
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.level.node.RectangleNode

sealed interface Action<out P> {

    data class SelectObject(val new: ObjectNode<*>?, val old: ObjectNode<*>?) : Action<Unit>

    data class DeleteObject(val obj: ObjectNode<*>) : Action<Unit>

    data class DrawRectangle(val start: Vector2fc, val end: Vector2fc) : Action<RectangleNode>

    data class DrawEllipse(val start: Vector2fc, val end: Vector2fc) : Action<EllipseNode>
}
