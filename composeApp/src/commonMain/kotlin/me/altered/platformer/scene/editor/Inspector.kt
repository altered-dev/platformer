package me.altered.platformer.scene.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.altered.platformer.Res
import me.altered.platformer.angle
import me.altered.platformer.corner
import me.altered.platformer.level.objects.MutableEllipse
import me.altered.platformer.level.objects.MutableObject
import me.altered.platformer.level.objects.MutableRectangle
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
        selectionState.selection2.singleOrNull()?.let { obj ->
            CommonInfo(obj, timelineState)
            when (obj) {
                is MutableRectangle -> RectangleInfo(obj, timelineState)
                is MutableEllipse -> EllipseInfo(obj, timelineState)
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
            Icon(painterResource(Res.drawable.angle), null, tint = Color(0xFFCCCCCC))
        }
        FloatTextField(
            state = obj.cornerRadius,
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            Icon(painterResource(Res.drawable.corner), null, tint = Color(0xFFCCCCCC))
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
            Icon(painterResource(Res.drawable.angle), null, tint = Color(0xFFCCCCCC))
        }
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
private fun IconText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Color(0xFFCCCCCC),
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        lineHeight = 12.sp,
    )
}
