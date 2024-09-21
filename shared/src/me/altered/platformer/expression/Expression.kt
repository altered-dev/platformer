package me.altered.platformer.expression

import kotlinx.serialization.Serializable

/**
 * A computable value to be used as a property of an object in a level.
 * TODO: serialize
 */
@Serializable
sealed interface Expression<out T> {

    fun eval(time: Float): T
}
