package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.altered.platformer.action.Action
import me.altered.platformer.action.FireTrigger
import me.altered.platformer.level.data.CollisionFlags
import me.altered.platformer.level.data.CollisionInfo
import me.altered.platformer.level.data.Object

sealed interface ObjectNode {
    val obj: Object
    val parent: GroupNode?

    val position: Offset
    val rotation: Float
    val bounds: Rect

    val globalPosition: Offset
        get() = position + (parent?.globalPosition ?: Offset.Zero)

    val globalBounds: Rect
        get() = bounds.translate(globalPosition)

    fun eval(time: Float) = Unit

    fun toMutableObjectNode(): MutableObjectNode

    sealed interface HasCornerRadius {

        val cornerRadius: Float
    }

    sealed interface HasFill {

        val fill: List<Brush>
    }

    sealed interface HasStroke {

        val stroke: List<Brush>
        val strokeWidth: Float
    }

    sealed interface HasCollision {

        val collisionFlags: CollisionFlags
        val isDamaging: Boolean

        fun collide(position: Offset, radius: Float): List<CollisionInfo>
    }

    sealed interface EditorDrawable {

        fun DrawScope.drawInEditor()
    }

    sealed interface Drawable {

        fun DrawScope.draw()
    }

    sealed interface HasActions {

        val actions: List<Action>

        fun fireAction(trigger: FireTrigger)
    }
}
