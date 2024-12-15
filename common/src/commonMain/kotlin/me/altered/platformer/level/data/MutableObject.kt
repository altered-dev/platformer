package me.altered.platformer.level.data

import androidx.compose.runtime.State
import me.altered.platformer.expression.AnimatedBrushState
import me.altered.platformer.expression.AnimatedFloatState

/**
 * A mutable representation of a level object.
 * Mostly used in the editor and may not be correctly serializable,
 * thus [toObject] is required before serialization.
 *
 * Any mutations happening within the object must be reported to composition via [State].
 *
 * @see Object
 */
sealed interface MutableObject : Object {

    // Technical and editor-only properties

    override var name: String

    // Animatable properties

    override val x: AnimatedFloatState
    override val y: AnimatedFloatState
    override val rotation: AnimatedFloatState
    override val width: AnimatedFloatState
    override val height: AnimatedFloatState

    sealed interface HasFill : Object.HasFill {

        override val fill: MutableList<AnimatedBrushState>
    }

    sealed interface HasStroke : Object.HasStroke {

        override val stroke: MutableList<AnimatedBrushState>
        override val strokeWidth: AnimatedFloatState
    }

    sealed interface HasCollision : Object.HasCollision {

        override var collisionFlags: CollisionFlags
        override var isDamaging: Boolean
    }

    // Conversions

    fun toObject(): Object

    override fun toMutableObject() = this
}
