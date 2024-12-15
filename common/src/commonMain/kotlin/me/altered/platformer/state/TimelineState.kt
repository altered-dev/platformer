package me.altered.platformer.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import me.altered.platformer.util.round

@Stable
class TimelineState(
    initialTime: Float,
    initialOffset: Float,
) {

    var time by mutableStateOf(initialTime)
    var offset by mutableStateOf(initialOffset)

    val roundedTime by derivedStateOf { time.round() }
}

@Composable
fun rememberTimelineState(
    initialTime: Float = 0.0f,
    initialOffset: Float = 0.0f,
) = remember { TimelineState(initialTime, initialOffset) }
