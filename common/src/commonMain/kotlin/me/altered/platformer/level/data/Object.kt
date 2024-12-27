package me.altered.platformer.level.data

import androidx.compose.ui.geometry.Rect
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.action.Action
import me.altered.platformer.expression.Expression

/**
 * An immutable representation of a level object.
 * Mostly used in actual gameplay and is serializable.
 *
 * @see MutableObject
 */
@Serializable
@SerialName("object")
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

    sealed interface HasCornerRadius : Object {

        val cornerRadius: Expression<Float>
    }

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
    }

    sealed interface HasActions : Object {

        val actions: List<Action>
    }

    // Conversions

    fun toMutableObject(): MutableObject

    companion object {

        val baseBounds = Rect(-0.5f, -0.5f, 0.5f, 0.5f)
    }
}
