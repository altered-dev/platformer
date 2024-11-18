package me.altered.platformer.scene.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import me.altered.platformer.engine.geometry.scale
import me.altered.platformer.level.data.Object
import me.altered.platformer.level.data.Rectangle
import me.altered.platformer.level.node.EllipseNode
import me.altered.platformer.level.node.GroupNode
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.level.node.RectangleNode
import org.jetbrains.compose.resources.painterResource

@Composable
fun Inspector(
    timelineState: TimelineState,
    objects: List<ObjectNode<*>>,
) {
    Column(
        modifier = Modifier
            .width(256.dp)
            .fillMaxHeight()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        objects.singleOrNull()?.let { obj ->
            CommonInfo(obj, timelineState)
            when (obj) {
                is EllipseNode -> EllipseInfo(obj, timelineState)
                is GroupNode -> GroupInfo(obj, timelineState)
                is RectangleNode -> RectangleInfo(obj, timelineState)
            }
            if (obj is ObjectNode.Filled) {
                (obj as? ObjectNode<Object.Filled>)?.let {
                    FilledInfo(obj, timelineState)
                }
            }
        }
    }
}

@Composable
private fun CommonInfo(
    node: ObjectNode<*>,
    timelineState: TimelineState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            node = node,
            expression = Object::x,
            value = { position.x },
            onValueChanged = { position = position.copy(x = it) },
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            IconText("X")
        }
        FloatTextField(
            node = node,
            expression = Object::y,
            value = { position.y },
            onValueChanged = { position = position.copy(y = it) },
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            IconText("Y")
        }
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            node = node,
            expression = Object::width,
            value = { bounds.width },
            onValueChanged = { bounds = ObjectNode.baseBounds.scale(it, bounds.height) },
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            IconText("W")
        }
        FloatTextField(
            node = node,
            expression = Object::height,
            value = { bounds.height },
            onValueChanged = { bounds = ObjectNode.baseBounds.scale(bounds.width, it) },
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            IconText("H")
        }
    }
}

@Composable
private fun EllipseInfo(
    node: EllipseNode,
    timelineState: TimelineState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            node = node,
            expression = Object::rotation,
            value = { rotation },
            onValueChanged = { rotation = it },
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            Icon(painterResource(Res.drawable.angle), null, tint = Color(0xFFCCCCCC))
        }
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
private fun GroupInfo(
    node: GroupNode,
    timelineState: TimelineState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            node = node,
            expression = Object::rotation,
            value = { rotation },
            onValueChanged = { rotation = it },
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            Icon(painterResource(Res.drawable.angle), null, tint = Color(0xFFCCCCCC))
        }
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
private fun RectangleInfo(
    node: RectangleNode,
    timelineState: TimelineState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            node = node,
            expression = Object::rotation,
            value = { rotation },
            onValueChanged = { rotation = it },
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            Icon(painterResource(Res.drawable.angle), null, tint = Color(0xFFCCCCCC))
        }
        FloatTextField(
            node = node,
            expression = Rectangle::cornerRadius,
            value = { cornerRadius },
            onValueChanged = { cornerRadius = it },
            timelineState = timelineState,
            modifier = Modifier.weight(1.0f),
        ) {
            Icon(painterResource(Res.drawable.corner), null, tint = Color(0xFFCCCCCC))
        }
    }
}

@Composable
fun <N> FilledInfo(
    node: N,
    timelineState: TimelineState,
    modifier: Modifier = Modifier,
) where N : ObjectNode<Object.Filled>, N : ObjectNode.Filled {
    ColorTextField(
        node = node,
        expression = { fill },
        value = { fill },
        onValueChanged = { fill = it },
        timelineState = timelineState,
        modifier = modifier.fillMaxWidth(),
    )
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
