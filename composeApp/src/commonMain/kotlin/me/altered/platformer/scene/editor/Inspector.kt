package me.altered.platformer.scene.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import me.altered.platformer.level.node.EllipseNode
import me.altered.platformer.level.node.GroupNode
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.level.node.RectangleNode
import me.altered.platformer.ui.TextField
import org.jetbrains.compose.resources.painterResource

@Composable
fun Inspector(
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
            CommonInfo(obj)
            when (obj) {
                is EllipseNode -> EllipseInfo(obj)
                is GroupNode -> GroupInfo(obj)
                is RectangleNode -> RectangleInfo(obj)
            }
        }
    }
}

@Composable
private fun CommonInfo(
    node: ObjectNode<*>,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val xState = remember(node) { TextFieldState(node.position.x.toString()) }
        val yState = remember(node) { TextFieldState(node.position.y.toString()) }

        BaseTextField(
            state = xState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val x = xState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.position = node.position.copy(x = x)
                it()
            },
        ) {
            IconText("X")
        }
        BaseTextField(
            state = yState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val y = yState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.position = node.position.copy(y = y)
                it()
            },
        ) {
            IconText("Y")
        }
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val wState = remember(node) { TextFieldState(node.bounds.width.toString()) }
        val hState = remember(node) { TextFieldState(node.bounds.height.toString()) }

        BaseTextField(
            state = wState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val w = wState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.bounds = ObjectNode.baseBounds.scale(w, node.bounds.height)
                it()
            },
        ) {
            IconText("W")
        }
        BaseTextField(
            state = hState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val h = hState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.bounds = ObjectNode.baseBounds.scale(node.bounds.width, h)
                it()
            },
        ) {
            IconText("H")
        }
    }
}

@Composable
private fun EllipseInfo(
    node: EllipseNode,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val rState = remember(node) { TextFieldState(node.rotation.toString()) }

        BaseTextField(
            state = rState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val r = rState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.rotation = r
                it()
            },
        ) {
            Icon(
                painter = painterResource(Res.drawable.angle),
                contentDescription = null,
                tint = Color(0xFFCCCCCC),
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
private fun GroupInfo(
    node: GroupNode,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val rState = remember(node) { TextFieldState(node.rotation.toString()) }

        BaseTextField(
            state = rState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val r = rState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.rotation = r
                it()
            },
        ) {
            Icon(
                painter = painterResource(Res.drawable.angle),
                contentDescription = null,
                tint = Color(0xFFCCCCCC),
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
private fun RectangleInfo(
    node: RectangleNode,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val rState = remember(node) { TextFieldState(node.rotation.toString()) }
        val cState = remember(node) { TextFieldState(node.cornerRadius.toString()) }

        BaseTextField(
            state = rState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val r = rState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.rotation = r
                it()
            },
        ) {
            Icon(
                painter = painterResource(Res.drawable.angle),
                contentDescription = null,
                tint = Color(0xFFCCCCCC),
            )
        }
        BaseTextField(
            state = cState,
            modifier = Modifier.weight(1.0f),
            onKeyboardAction = {
                val c = cState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.cornerRadius = c
                it()
            },
        ) {
            Icon(
                painter = painterResource(Res.drawable.corner),
                contentDescription = null,
                tint = Color(0xFFCCCCCC),
            )
        }
    }
}

@Composable
private fun IconText(text: String) {
    Text(
        text = text,
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
