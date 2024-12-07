package me.altered.platformer.scene.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
//import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.altered.platformer.Res
import me.altered.platformer.angle
import me.altered.platformer.corner
import me.altered.platformer.engine.geometry.scale
import me.altered.platformer.expression.AnimatedBrushState
import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.expression.const
import me.altered.platformer.level.data.Object
import me.altered.platformer.level.data.solid
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
        val xState = remember(node.id, timelineState.roundedTime) { TextFieldState(node.position.x.toString()) }
        val yState = remember(node.id, timelineState.roundedTime) { TextFieldState(node.position.y.toString()) }

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
                    .pointerHoverIcon(PointerIcon.Hand)
                    .drag(node.id) { delta ->
                        val x = (xState.text.toString().toFloatOrNull() ?: return@drag) + delta / 100.0f
                        node.position = node.position.copy(x = x)
                        (node.obj?.x as? AnimatedFloatState)?.staticValue = const(x)
                        xState.setTextAndPlaceCursorAtEnd(x.toString())
                    }
                    .floatKeyframe(
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
                    .pointerHoverIcon(PointerIcon.Hand)
                    .drag(node.id) { delta ->
                        val y = (yState.text.toString().toFloatOrNull() ?: return@drag) + delta / 100.0f
                        node.position = node.position.copy(y = y)
                        (node.obj?.y as? AnimatedFloatState)?.staticValue = const(y)
                        yState.setTextAndPlaceCursorAtEnd(y.toString())
                    }
                    .floatKeyframe(
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
        val wState = remember(node.id, timelineState.roundedTime) { TextFieldState(node.bounds.width.toString()) }
        val hState = remember(node.id, timelineState.roundedTime) { TextFieldState(node.bounds.height.toString()) }

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
                    .pointerHoverIcon(PointerIcon.Hand)
                    .drag(node.id) { delta ->
                        val w = (wState.text.toString().toFloatOrNull() ?: return@drag) + delta / 100.0f
                        node.bounds = ObjectNode.baseBounds.scale(w, node.bounds.height)
                        (node.obj?.width as? AnimatedFloatState)?.staticValue = const(w)
                        wState.setTextAndPlaceCursorAtEnd(w.toString())
                    }
                    .floatKeyframe(
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
                    .pointerHoverIcon(PointerIcon.Hand)
                    .drag(node.id) { delta ->
                        val h = (hState.text.toString().toFloatOrNull() ?: return@drag) + delta / 100.0f
                        node.bounds = ObjectNode.baseBounds.scale(node.bounds.width, h)
                        (node.obj?.height as? AnimatedFloatState)?.staticValue = const(h)
                        hState.setTextAndPlaceCursorAtEnd(h.toString())
                    }
                    .floatKeyframe(
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
        val rState = remember(node.id, timelineState.roundedTime) { TextFieldState(node.rotation.toString()) }

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
                    .pointerHoverIcon(PointerIcon.Hand)
                    .drag(node.id) { delta ->
                        val r = (rState.text.toString().toFloatOrNull() ?: return@drag) + delta / 50.0f
                        node.rotation = r
                        (node.obj?.rotation as? AnimatedFloatState)?.staticValue = const(r)
                        rState.setTextAndPlaceCursorAtEnd(r.toString())
                    }
                    .floatKeyframe(
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
        val rState = remember(node.id, timelineState.roundedTime) { TextFieldState(node.rotation.toString()) }

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
                    .pointerHoverIcon(PointerIcon.Hand)
                    .drag(node.id) { delta ->
                        val r = (rState.text.toString().toFloatOrNull() ?: return@drag) + delta / 50.0f
                        node.rotation = r
                        (node.obj?.rotation as? AnimatedFloatState)?.staticValue = const(r)
                        rState.setTextAndPlaceCursorAtEnd(r.toString())
                    }
                    .floatKeyframe(
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
        val rState = remember(node.id, timelineState.roundedTime) { TextFieldState(node.rotation.toString()) }
        val cState = remember(node.id, timelineState.roundedTime) { TextFieldState(node.cornerRadius.toString()) }

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
                    .pointerHoverIcon(PointerIcon.Hand)
                    .drag(node.id) { delta ->
                        val r = (rState.text.toString().toFloatOrNull() ?: return@drag) + delta / 50.0f
                        node.rotation = r
                        (node.obj?.rotation as? AnimatedFloatState)?.staticValue = const(r)
                        rState.setTextAndPlaceCursorAtEnd(r.toString())
                    }
                    .floatKeyframe(
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
                    .pointerHoverIcon(PointerIcon.Hand)
                    .drag(node.id) { delta ->
                        val c = (cState.text.toString().toFloatOrNull() ?: return@drag) + delta / 100.0f
                        node.cornerRadius = c
                        (node.obj?.cornerRadius as? AnimatedFloatState)?.staticValue = const(c)
                        cState.setTextAndPlaceCursorAtEnd(c.toString())
                    }
                    .floatKeyframe(
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

@OptIn(ExperimentalStdlibApi::class)
private val HexFormat = HexFormat {
    upperCase = true
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun <N> FilledInfo(
    node: N,
    timelineState: TimelineState,
    modifier: Modifier = Modifier,
) where N : ObjectNode<Object.Filled>, N : ObjectNode.Filled {
    val fillState = remember(node.id) { TextFieldState((node.fill as? SolidColor)?.value?.toArgb()?.toHexString(HexFormat) ?: "FFFFFFFF") }

    BaseTextField(
        state = fillState,
        modifier = modifier.fillMaxWidth(),
        onKeyboardAction = {
            val fill = runCatching { fillState.text.toString().hexToInt(HexFormat) }.getOrElse { return@BaseTextField }
            node.fill = SolidColor(Color(fill))
            (node.obj?.fill as? AnimatedBrushState)?.staticValue = const(solid(fill.toLong()))
            it()
        },
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(2.dp))
                .background(node.fill)
                .pointerHoverIcon(PointerIcon.Hand)
                .brushKeyframe(
                    state = timelineState,
                    node = node,
                    expression = { fill },
                    value = { solid((fill as? SolidColor)?.value?.toArgb() ?: 0) }
                )
        )
    }
}

@Composable
private fun IconText(
    text: String,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalTextStyle provides TextStyle(
            color = Color(0xFFCCCCCC),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            lineHeight = 12.sp
        )
    ) {
        BasicText(
            text = text,
            modifier = modifier,
            style = LocalTextStyle.current
        )
    }
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

private fun Modifier.drag(
    key: Any?,
    onDrag: (Float) -> Unit,
) = pointerInput(key) {
    detectHorizontalDragGestures { change, dragAmount ->
        onDrag(dragAmount)
    }
}
