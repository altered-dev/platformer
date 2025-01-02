package me.altered.platformer.action

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import me.altered.platformer.action.effect.MutableEffect

class MutableAction(
    trigger: Trigger,
    override val effects: SnapshotStateList<MutableEffect<*>> = mutableStateListOf(),
) : Action(trigger, effects) {

    override var trigger by mutableStateOf(trigger)

    fun toAction() = Action(
        trigger = trigger,
        effects = effects.map { it.toEffect() },
    )
}
