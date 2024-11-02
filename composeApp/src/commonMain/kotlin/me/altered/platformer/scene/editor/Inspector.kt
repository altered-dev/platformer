package me.altered.platformer.scene.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import me.altered.platformer.geometry.scale
import me.altered.platformer.level.node.EllipseNode
import me.altered.platformer.level.node.GroupNode
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.level.node.RectangleNode
import me.altered.platformer.ui.TextField

@Composable
fun Inspector(
    objects: List<Pair<ObjectNode<*>, Rect>>,
) {
    Column(
        modifier = Modifier
            .width(256.dp)
            .fillMaxHeight()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        objects.singleOrNull()?.let { (obj, _) ->
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
private fun ColumnScope.CommonInfo(
    node: ObjectNode<*>,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val xState = remember(node) { TextFieldState(node.position.x.toString()) }
        val yState = remember(node) { TextFieldState(node.position.y.toString()) }

        BaseTextField(
            state = xState,
            onKeyboardAction = {
                val x = xState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.position = node.position.copy(x = x)
                it()
            },
        ) {
            Text("X")
        }
        BaseTextField(
            state = yState,
            onKeyboardAction = {
                val y = yState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.position = node.position.copy(y = y)
                it()
            },
        ) {
            Text("Y")
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val wState = remember(node) { TextFieldState(node.bounds.width.toString()) }
        val hState = remember(node) { TextFieldState(node.bounds.height.toString()) }

        BaseTextField(
            state = wState,
            onKeyboardAction = {
                val w = wState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.bounds = ObjectNode.baseBounds.scale(w, node.bounds.height)
                it()
            },
        ) {
            Text("W")
        }
        BaseTextField(
            state = hState,
            onKeyboardAction = {
                val h = hState.text.toString().toFloatOrNull() ?: return@BaseTextField
                node.bounds = ObjectNode.baseBounds.scale(node.bounds.width, h)
                it()
            },
        ) {
            Text("H")
        }
    }
}

@Composable
private fun ColumnScope.EllipseInfo(
    node: EllipseNode,
) {
}

@Composable
private fun ColumnScope.GroupInfo(
    node: GroupNode,
) {

}

@Composable
private fun ColumnScope.RectangleInfo(
    node: RectangleNode,
) {

}

@Composable
private fun RowScope.BaseTextField(
    state: TextFieldState,
    onKeyboardAction: KeyboardActionHandler? = null,
    icon: @Composable BoxScope.() -> Unit = {},
) {
    TextField(
        state = state,
        modifier = Modifier.weight(1.0f),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        onKeyboardAction = onKeyboardAction,
        lineLimits = TextFieldLineLimits.SingleLine,
        icon = icon,
    )
}
