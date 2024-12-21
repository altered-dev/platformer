package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.altered.platformer.level.data.MutableObject
import me.altered.platformer.level.node.MutableObjectNode
import me.altered.platformer.scene.editor.state.SelectionState
import me.altered.platformer.state.TimelineState

@Composable
fun Inspector(
    selectionState: SelectionState,
    timelineState: TimelineState,
) {
    Column(
        modifier = Modifier
            .width(256.dp)
            .fillMaxHeight()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        when (selectionState.selection.size) {
            0 -> Unit // TODO: level properties
            1 -> {
                val node = selectionState.selection.single()
                CommonInfo(node, timelineState)
            }
            else -> Unit // TODO: multiselection
        }
    }
}

@Composable
private fun CommonInfo(
    node: MutableObjectNode,
    timelineState: TimelineState,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            state = node.obj.x,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        )
        FloatTextField(
            state = node.obj.y,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            state = node.obj.width,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        )
        FloatTextField(
            state = node.obj.height,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            state = node.obj.rotation,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        )
        when (val obj = node.obj) {
            is MutableObject.HasCornerRadius -> {
                FloatTextField(
                    state = obj.cornerRadius,
                    timelineState = timelineState,
                    modifier = Modifier.weight(1.0f),
                )
            }
            else -> Spacer(modifier = Modifier.weight(1.0f))
        }
    }
}
