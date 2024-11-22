package me.altered.platformer.level.objects

import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlinx.serialization.Serializable

/**
 * An immutable representation of a fully loaded level.
 *
 * @see MutableLevel
 */
@Serializable
sealed interface Level {

    val name: String
    val objects: List<Object>

    fun DrawScope.draw()

    /**
     * Evaluates all objects in the level.
     *
     * NOTE: **DO NOT** optimize for time equality, as some expressions may rely not just on time.
     */
    fun eval(time: Float)

    fun toMutableLevel(): MutableLevel
}
