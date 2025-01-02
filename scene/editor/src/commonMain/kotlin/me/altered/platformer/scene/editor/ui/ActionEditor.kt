package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import me.altered.platformer.action.MutableAction
import me.altered.platformer.action.effect.MutableEffect
import me.altered.platformer.action.effect.MutableMoveBy
import me.altered.platformer.action.effect.MutableRotateBy
import me.altered.platformer.expression.InspectorInfo
import me.altered.platformer.resources.Res
import me.altered.platformer.resources.remove
import me.altered.platformer.resources.time
import me.altered.platformer.state.rememberTransformState
import me.altered.platformer.state.transform
import me.altered.platformer.ui.Icon
import me.altered.platformer.ui.IconButton
import me.altered.platformer.ui.Text
import org.jetbrains.compose.resources.painterResource

private val NodeWidth = 304.dp

@Composable
internal fun ActionEditor(
    state: ActionEditorState,
    modifier: Modifier = Modifier,
) {
    val action = requireNotNull(state.action)
    val transform = rememberTransformState()
    Box(
        modifier = modifier
            .background(Color(0xFF2E2E2E))
            .pan(transform),
    ) {
        // TODO: to custom layout for node placement
        Column(
            modifier = Modifier
                .fillMaxSize()
                .transform(transform),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TriggerNode(action)
            action.effects.forEach { effect ->
                EffectNode(
                    effect = effect,
                    onRemove = { action.effects -= effect },
                )
            }
        }
    }
}

@Composable
private fun TriggerNode(
    action: MutableAction,
) = Node {
    Row(
        modifier = Modifier.padding(start = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(painterResource(Res.drawable.time))
        Text("When")
        Spacer(modifier = Modifier.weight(1.0f))

        // TODO: to dropdown
        Row(
            modifier = Modifier
                .border(1.dp, Color(0xFF262626), RoundedCornerShape(8.dp))
                .padding(10.dp, 12.dp, 12.dp, 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            InspectorIcon(action.trigger.inspectorInfo)
            Text(action.trigger.inspectorInfo.name)
        }
    }
}

@Composable
private fun EffectNode(
    effect: MutableEffect<*>,
    onRemove: () -> Unit,
) = Node {
    Row(
        modifier = Modifier.padding(start = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InspectorIcon(effect.inspectorInfo)
        Text(effect.inspectorInfo.name)
        Spacer(modifier = Modifier.weight(1.0f))
        IconButton(onClick = onRemove) {
            Icon(painterResource(Res.drawable.remove))
        }
    }
    when (effect) {
        is MutableMoveBy -> MoveByInfo(effect)
        is MutableRotateBy -> RotateByInfo(effect)
    }
}

@Composable
private fun MoveByInfo(
    effect: MutableMoveBy,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            value = effect.x,
            onValueChange = { effect.x = it },
            inspectorInfo = InspectorInfo.X,
            modifier = Modifier.weight(1.0f),
        )
        FloatTextField(
            value = effect.y,
            onValueChange = { effect.y = it },
            inspectorInfo = InspectorInfo.Y,
            modifier = Modifier.weight(1.0f),
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            value = effect.duration,
            onValueChange = { effect.duration = it },
            inspectorInfo = InspectorInfo.Duration,
            modifier = Modifier.weight(1.0f),
        )
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
private fun RotateByInfo(
    effect: MutableRotateBy,
) {
    FloatTextField(
        value = effect.angle,
        onValueChange = { effect.angle = it },
        inspectorInfo = InspectorInfo.Rotation,
        modifier = Modifier.fillMaxWidth(),
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FloatTextField(
            value = effect.duration,
            onValueChange = { effect.duration = it },
            inspectorInfo = InspectorInfo.Duration,
            modifier = Modifier.weight(1.0f),
        )
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
private fun Node(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .width(NodeWidth)
            .background(Color(0xFF333333), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF262626), RoundedCornerShape(8.dp))
            .padding(8.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = content,
    )
}
