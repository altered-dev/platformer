package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.altered.platformer.expression.AnimatedBrushState
import me.altered.platformer.expression.InspectorInfo
import me.altered.platformer.level.data.MutableObject
import me.altered.platformer.level.data.solid
import me.altered.platformer.level.node.MutableLevelNode
import me.altered.platformer.level.node.MutableObjectNode
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.resources.Res
import me.altered.platformer.resources.add
import me.altered.platformer.resources.remove
import me.altered.platformer.scene.editor.state.SelectionState
import me.altered.platformer.state.TimelineState
import me.altered.platformer.ui.Icon
import me.altered.platformer.ui.IconButton
import me.altered.platformer.ui.Text
import org.jetbrains.compose.resources.painterResource

@Composable
fun Inspector(
    level: MutableLevelNode,
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
            0 -> LevelInfo(level, timelineState)
            1 -> {
                val node = selectionState.selection.single()
                CommonInfo(node, timelineState)
            }
            else -> Unit // TODO: multiselection
        }
    }
}

@Composable
private fun LevelInfo(
    level: MutableLevelNode,
    timelineState: TimelineState,
) {
    BrushTextField(
        state = level.level.background,
        timelineState = timelineState,
        modifier = Modifier
            .fillMaxWidth(),
    )
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
    if (node is ObjectNode.HasFill) {
        Separator()
        FillInfo(node, timelineState)
    }
    if (node is ObjectNode.HasStroke) {
        Separator()
        StrokeInfo(node, timelineState)
        val obj = node.obj as MutableObject.HasStroke
        FloatTextField(
            state = obj.strokeWidth,
            timelineState = timelineState,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun FillInfo(
    node: MutableObjectNode,
    timelineState: TimelineState,
) {
    // no union types :(
    require(node is ObjectNode.HasFill)
    val obj = node.obj as MutableObject.HasFill
    Section(
        onAddClick = { obj.fill += AnimatedBrushState(solid(0xFFCCCCCC), InspectorInfo.Fill) },
        info = InspectorInfo.Fill,
    )
    obj.fill.forEach { fill ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BrushTextField(
                state = fill,
                timelineState = timelineState,
                modifier = Modifier.weight(1.0f),
            )
            IconButton(
                onClick = { obj.fill -= fill },
            ) {
                Icon(painterResource(Res.drawable.remove))
            }
        }
    }
}

@Composable
private fun StrokeInfo(
    node: MutableObjectNode,
    timelineState: TimelineState,
) {
    // no union types :(
    require(node is ObjectNode.HasStroke)
    val obj = node.obj as MutableObject.HasStroke
    Section(
        onAddClick = { obj.stroke += AnimatedBrushState(solid(0xFF000000), InspectorInfo.Outline) },
        info = InspectorInfo.Outline,
    )
    obj.stroke.forEach { stroke ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BrushTextField(
                state = stroke,
                timelineState = timelineState,
                modifier = Modifier.weight(1.0f),
            )
            IconButton(
                onClick = { obj.stroke -= stroke },
            ) {
                Icon(painterResource(Res.drawable.remove))
            }
        }
    }
}

@Composable
private fun Separator() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .height(1.dp)
            .background(Color(0xFF262626), CircleShape)
    )
}

@Composable
private fun Section(
    onAddClick: () -> Unit,
    info: InspectorInfo,
) {
    Row(
        modifier = Modifier
            .height(36.dp)
            .padding(start = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InspectorIcon(info)
        Text(info.name)
        Spacer(modifier = Modifier.weight(1.0f))
        IconButton(onClick = onAddClick) {
            Icon(painterResource(Res.drawable.add))
        }
    }
}
