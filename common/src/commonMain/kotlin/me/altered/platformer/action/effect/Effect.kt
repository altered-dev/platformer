package me.altered.platformer.action.effect

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A unit of operation of an action.
 */
@Serializable
@SerialName("effect")
sealed interface Effect<out T : Any> {

    // TODO: to expression
    val duration: Float
    val target: Target

    fun produce(time: Float): T

    fun toMutableEffect(): MutableEffect<T>

    /**
     * The object that the effect will be performed on.
     */
    @Serializable
    @SerialName("target")
    sealed interface Target {

        /**
         * The object that contains this action.
         */
        @Serializable
        @SerialName("self")
        data object Self : Target
    }
}
