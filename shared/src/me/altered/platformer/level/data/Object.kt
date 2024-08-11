package me.altered.platformer.level.data

import kotlinx.serialization.Serializable
import me.altered.platformer.editor.Brush
import me.altered.platformer.timeline.Expression

/**
 * A descriptor of an object inside a level.
 * All properties are expressions in order to allow animations.
 */
@Serializable
sealed interface Object {

    val name: String
    val x: Expression<Float>
    val y: Expression<Float>
    val rotation: Expression<Float>
    val width: Expression<Float>
    val height: Expression<Float>

    sealed interface Filled : Object {

        val fill: Expression<Brush>
    }

    sealed interface Stroked : Object {

        val stroke: Expression<Brush>
        val strokeWidth: Expression<Float>
    }
}
