package me.altered.platformer.scene.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.altered.platformer.Res
import me.altered.platformer.angle
import me.altered.platformer.corner
import me.altered.platformer.engine.geometry.scale
import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.expression.const
import me.altered.platformer.level.node.EllipseNode
import me.altered.platformer.level.node.GroupNode
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.level.node.RectangleNode
import me.altered.platformer.ui.TextField
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
        val xState = remember(timelineState.roundedTime) { TextFieldState(node.position.x.toString()) }
        val yState = remember(timelineState.roundedTime) { TextFieldState(node.position.y.toString()) }

        BaseTextField(
            state = xState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val x = xState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.position = node.position.copy(x = x)
                (node.obj?.x as? AnimatedFloatState)?.staticValue = const(x)
                it()
            },
        ) {
            IconText(
                text = "X",
                modifier = Modifier
                    .fillMaxSize()
                    .addKeyframe(
                        state = timelineState,
                        node = node,
                        expression = { x },
                        value = { position.x },
                    ),
            )
        }
        BaseTextField(
            state = yState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val y = yState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.position = node.position.copy(y = y)
                (node.obj?.y as? AnimatedFloatState)?.staticValue = const(y)
                it()
            },
        ) {
            IconText(
                text = "Y",
                modifier = Modifier
                    .fillMaxSize()
                    .addKeyframe(
                        state = timelineState,
                        node = node,
                        expression = { y },
                        value = { position.y },
                    ),
            )
        }
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val wState = remember(timelineState.roundedTime) { TextFieldState(node.bounds.width.toString()) }
        val hState = remember(timelineState.roundedTime) { TextFieldState(node.bounds.height.toString()) }

        BaseTextField(
            state = wState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val w = wState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.bounds = ObjectNode.baseBounds.scale(w, node.bounds.height)
                (node.obj?.width as? AnimatedFloatState)?.staticValue = const(w)
                it()
            },
        ) {
            IconText(
                text = "W",
                modifier = Modifier
                    .fillMaxSize()
                    .addKeyframe(
                        state = timelineState,
                        node = node,
                        expression = { width },
                        value = { bounds.width },
                    ),
            )
        }
        BaseTextField(
            state = hState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val h = hState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.bounds = ObjectNode.baseBounds.scale(node.bounds.width, h)
                (node.obj?.height as? AnimatedFloatState)?.staticValue = const(h)
                it()
            },
        ) {
            IconText(
                text = "H",
                modifier = Modifier
                    .fillMaxSize()
                    .addKeyframe(
                        state = timelineState,
                        node = node,
                        expression = { height },
                        value = { bounds.height },
                    ),
            )
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
        val rState = remember(timelineState.roundedTime) { TextFieldState(node.rotation.toString()) }

        BaseTextField(
            state = rState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val r = rState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.rotation = r
                (node.obj?.rotation as? AnimatedFloatState)?.staticValue = const(r)
                it()
            },
        ) {
            Icon(
                painter = painterResource(Res.drawable.angle),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .addKeyframe(
                        state = timelineState,
                        node = node,
                        expression = { rotation },
                        value = { rotation },
                    ),
                tint = Color(0xFFCCCCCC),
            )
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
        val rState = remember(timelineState.roundedTime) { TextFieldState(node.rotation.toString()) }

        BaseTextField(
            state = rState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val r = rState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.rotation = r
                (node.obj?.rotation as? AnimatedFloatState)?.staticValue = const(r)
                it()
            },
        ) {
            Icon(
                painter = painterResource(Res.drawable.angle),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .addKeyframe(
                        state = timelineState,
                        node = node,
                        expression = { rotation },
                        value = { rotation },
                    ),
                tint = Color(0xFFCCCCCC),
            )
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
        val rState = remember(timelineState.roundedTime) { TextFieldState(node.rotation.toString()) }
        val cState = remember(timelineState.roundedTime) { TextFieldState(node.cornerRadius.toString()) }

        BaseTextField(
            state = rState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val r = rState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.rotation = r
                (node.obj?.rotation as? AnimatedFloatState)?.staticValue = const(r)
                it()
            },
        ) {
            Icon(
                painter = painterResource(Res.drawable.angle),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .addKeyframe(
                        state = timelineState,
                        node = node,
                        expression = { rotation },
                        value = { rotation },
                    ),
                tint = Color(0xFFCCCCCC),
            )
        }
        BaseTextField(
            state = cState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val c = cState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.cornerRadius = c
                (node.obj?.cornerRadius as? AnimatedFloatState)?.staticValue = const(c)
                it()
            },
        ) {
            Icon(
                painter = painterResource(Res.drawable.corner),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .addKeyframe(
                        state = timelineState,
                        node = node,
                        expression = { cornerRadius },
                        value = { cornerRadius },
                    ),
                tint = Color(0xFFCCCCCC),
            )
        }
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

@Composable
private fun BaseTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    onKeyboardAction: KeyboardActionHandler? = null,
    icon: @Composable BoxScope.() -> Unit = {},
) {
    TextField(
        state = state,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        onKeyboardAction = onKeyboardAction,
        lineLimits = TextFieldLineLimits.SingleLine,
        icon = icon,
    )
}
