package me.altered.platformer.editor.action

import me.altered.koml.Vector2f
import me.altered.platformer.`object`.Ellipse
import me.altered.platformer.`object`.ObjectNode
import me.altered.platformer.`object`.Rectangle

sealed interface Action<out P> {

    data class SelectObject(val new: ObjectNode?, val old: ObjectNode?) : Action<Unit>

    data class DrawRectangle(val start: Vector2f, val end: Vector2f) : Action<Rectangle>

    data class DrawEllipse(val start: Vector2f, val end: Vector2f) : Action<Ellipse>
}
