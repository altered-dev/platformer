package me.altered.platformer.action

import androidx.compose.runtime.mutableStateListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.action.effect.Effect

@Serializable
@SerialName("action")
open class Action(
    open val trigger: Trigger,
    open val effects: List<Effect<*>>,
) {

    open fun toMutableAction() = MutableAction(
        trigger = trigger,
        effects = effects.mapTo(mutableStateListOf()) { it.toMutableEffect() },
    )
}
