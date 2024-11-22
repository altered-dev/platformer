package me.altered.platformer.level

import me.altered.platformer.level.objects.MutableObject

/**
 * A mutable representation of a fully loaded level.
 * Mostly used in the editor and may not be correctly serializable,
 * thus [toLevel] is required before serialization.
 *
 * @see Level
 */
sealed interface MutableLevel : Level {

    override var name: String
    override val objects: MutableList<MutableObject>

    fun toLevel(): Level

    override fun toMutableLevel() = this
}
