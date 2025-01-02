package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.altered.platformer.action.effect.MutableMoveBy
import me.altered.platformer.action.effect.MutableRotateBy
import me.altered.platformer.expression.InspectorInfo
import me.altered.platformer.ui.Text

@Composable
fun ActionNodeList(
    state: ActionEditorState,
) {
    val action = requireNotNull(state.action)
    Column(
        modifier = Modifier
            .width(256.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Item(
            inspectorInfo = InspectorInfo.Move,
            onClick = { action.effects += MutableMoveBy() },
        )
        Item(
            inspectorInfo = InspectorInfo.Rotate,
            onClick = { action.effects += MutableRotateBy() },
        )
    }
}

@Composable
private fun Item(
    inspectorInfo: InspectorInfo,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .border(1.dp, Color(0xFF262626), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InspectorIcon(inspectorInfo)
        Text(inspectorInfo.name)
    }
}