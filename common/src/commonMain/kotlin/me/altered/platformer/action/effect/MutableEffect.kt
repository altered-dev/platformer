package me.altered.platformer.action.effect

import me.altered.platformer.expression.InspectorInfo

sealed interface MutableEffect<out T : Any> : Effect<T> {

    val inspectorInfo: InspectorInfo

    override var duration: Float
    override var target: Effect.Target

    fun toEffect(): Effect<T>
}
