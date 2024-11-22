package me.altered.platformer.level

import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlinx.serialization.Serializable
import me.altered.platformer.level.objects.Object

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
