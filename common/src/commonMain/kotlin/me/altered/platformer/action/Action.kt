package me.altered.platformer.action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("action")
data class Action(
    val trigger: Trigger,
    val effects: List<Effect<*>>,
)
