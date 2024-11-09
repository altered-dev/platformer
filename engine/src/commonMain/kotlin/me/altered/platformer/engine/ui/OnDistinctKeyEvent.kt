package me.altered.platformer.engine.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type

/**
 * [Modifier.onKeyEvent] but filters out repeated key down events for convenience.
 */
fun Modifier.onDistinctKeyEvent(
    onKeyEvent: (KeyEvent) -> Boolean,
): Modifier = composed(
    inspectorInfo = {
        name = "onKeyEvent"
        properties["onKeyEvent"] = onKeyEvent
    },
) {
    val lambda by rememberUpdatedState(onKeyEvent)
    val keySet = mutableSetOf<Key>()
    onKeyEvent { event ->
        println("keySet = $keySet")
        println("${event.key} in keySet == ${event.key in keySet}")
        if (event.type == KeyEventType.KeyDown && event.key in keySet) {
            return@onKeyEvent false
        }
        when (event.type) {
            KeyEventType.KeyDown -> keySet += event.key
            KeyEventType.KeyUp -> keySet -= event.key
        }
        lambda(event)
    }
}
