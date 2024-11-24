package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.altered.platformer.Res
import me.altered.platformer.angle
import me.altered.platformer.corner
import me.altered.platformer.level.objects.MutableEllipse
import me.altered.platformer.level.objects.MutableGroup
import me.altered.platformer.level.objects.MutableObject
import me.altered.platformer.level.objects.MutableRectangle
import me.altered.platformer.scene.editor.state.SelectionState
import me.altered.platformer.scene.editor.state.TimelineState
import me.altered.platformer.ui.Icon
import org.jetbrains.compose.resources.painterResource

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
        selectionState.selection.singleOrNull()?.let { obj ->
            CommonInfo(obj, timelineState)
            when (obj) {
                is MutableRectangle -> RectangleInfo(obj, timelineState)
                is MutableEllipse -> EllipseInfo(obj, timelineState)
                is MutableGroup -> GroupInfo(obj, timelineState)
            }
        }
    }
}

@Composable
private fun CommonInfo(
    obj: MutableObject,
    timelineState: TimelineState,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            state = obj.x,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            IconText("X")
        }
        FloatTextField(
            state = obj.y,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            IconText("Y")
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            state = obj.width,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            IconText("W")
        }
        FloatTextField(
            state = obj.height,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            IconText("H")
        }
    }
}

@Composable
private fun RectangleInfo(
    obj: MutableRectangle,
    timelineState: TimelineState,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            state = obj.rotation,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            Icon(painterResource(Res.drawable.angle), tint = Color(0xFFCCCCCC))
        }
        FloatTextField(
            state = obj.cornerRadius,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            Icon(painterResource(Res.drawable.corner), tint = Color(0xFFCCCCCC))
        }
    }
}

@Composable
private fun EllipseInfo(
    obj: MutableEllipse,
    timelineState: TimelineState,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            state = obj.rotation,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            Icon(painterResource(Res.drawable.angle), tint = Color(0xFFCCCCCC))
        }
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
private fun GroupInfo(
    obj: MutableGroup,
    timelineState: TimelineState,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            state = obj.rotation,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            Icon(painterResource(Res.drawable.angle), tint = Color(0xFFCCCCCC))
        }
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
private fun IconText(
    text: String,
    modifier: Modifier = Modifier,
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = TextStyle(
            color = Color(0xFFCCCCCC),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            lineHeight = 12.sp,
        ),
    )
}
