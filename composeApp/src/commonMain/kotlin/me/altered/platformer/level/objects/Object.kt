package me.altered.platformer.level.objects

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlinx.serialization.Serializable
import me.altered.platformer.expression.Expression
import me.altered.platformer.level.data.Brush
import me.altered.platformer.level.data.CollisionFlags
import me.altered.platformer.level.data.CollisionInfo

/**
 * An immutable representation of a level object.
 * Mostly used in actual gameplay and is serializable.
 *
 * @see MutableObject
 */
@Serializable
sealed interface Object {

    // Technical and editor-only properties

    /**
     * The identifier of the object that is only used
     * to differentiate between objects and is not displayed anywhere.
     * It must be unique per level.
     */
    val id: Long

    /**
     * The name of the object that is only used
     * in the node tree tab of the editor and is customizable by the user.
     */
    val name: String

    // Animatable properties

    val x: Expression<Float>
    val y: Expression<Float>
    val rotation: Expression<Float>
    val width: Expression<Float>
    val height: Expression<Float>

    sealed interface HasFill : Object {

        val fill: List<Expression<Brush>>
    }

    sealed interface HasStroke : Object {

        val stroke: List<Expression<Brush>>
        val strokeWidth: Expression<Float>
    }

    sealed interface HasCollision : Object {

        val collisionFlags: CollisionFlags
        val isDamaging: Boolean

        /**
         * Check for collisions with a circle shape (the player).
         *
         * @return points and normals of the collisions, empty if no collision happened.
         */
        fun collide(position: Offset, radius: Float): List<CollisionInfo>
    }

    sealed interface EditorDrawable : Object {

        fun DrawScope.draw()
    }

    sealed interface Drawable : EditorDrawable

    /**
     * Evaluates the expressions into the internal state of the object.
     *
     * NOTE: **DO NOT** optimize for time equality, as some expressions may rely not just on time.
     */
    fun eval(time: Float)

    // Conversions

    fun toMutableObject(): MutableObject

    companion object {

        val baseBounds = Rect(-0.5f, -0.5f, 0.5f, 0.5f)
    }
}
