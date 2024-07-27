package me.altered.platformer.editor.action

import me.altered.koml.Vector2fc
import me.altered.platformer.objects.Ellipse
import me.altered.platformer.objects.ObjectNode
import me.altered.platformer.objects.Rectangle

sealed interface Action<out P> {

    data class SelectObject(val new: ObjectNode?, val old: ObjectNode?) : Action<Unit>

    data class DeleteObject(val obj: ObjectNode) : Action<Unit>

    data class DrawRectangle(val start: Vector2fc, val end: Vector2fc) : Action<Rectangle>

    data class DrawEllipse(val start: Vector2fc, val end: Vector2fc) : Action<Ellipse>
}
