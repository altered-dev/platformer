package me.altered.platformer.expression

import kotlinx.serialization.Serializable

/**
 * A computable value to be used as a property of an object in a level.
 *
 * NOTE: non-abstract classes must eliminate the type parameter
 * in order for serialization to work properly.
 */
@Serializable
sealed interface Expression<out T> {

    fun eval(time: Float): T
}
