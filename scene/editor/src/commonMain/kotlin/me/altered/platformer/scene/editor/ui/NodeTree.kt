package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.serialization.serializer
import me.altered.platformer.level.node.MutableGroupNode
import me.altered.platformer.level.node.MutableLevelNode
import me.altered.platformer.level.node.MutableObjectNode
import me.altered.platformer.scene.editor.state.SelectionState

@Composable
fun NodeTree(
    level: MutableLevelNode,
    selectionState: SelectionState,
) {
    val source = remember { MutableInteractionSource() }
    val hovered by source.collectIsHoveredAsState()
    LaunchedEffect(hovered) {
        if (hovered) selectionState.hovered = null
    }
    LazyColumn(
        modifier = Modifier
            .width(256.dp)
            .fillMaxHeight() 
            .hoverable(source)
            .clickable(source, null) {
                selectionState.deselect()
            },
        contentPadding = PaddingValues(8.dp),
    ) {
        items(level.objects, key = { it.obj.id }) {
            Object(
                node = it,
                selected = { it in selectionState.selection },
                onHover = { selectionState.hovered = it },
                onClick = { selectionState.selectSingle(it) },
            )
        }
        val a = serializer<Int>()
    }
}

@Composable
private fun Object(
    node: MutableObjectNode,
    selected: (MutableObjectNode) -> Boolean,
    onHover: (MutableObjectNode) -> Unit,
    onClick: (MutableObjectNode) -> Unit,
    modifier: Modifier = Modifier,
) {
    val source = remember { MutableInteractionSource() }
    val hovered by source.collectIsHoveredAsState()
    LaunchedEffect(hovered) {
        if (hovered) onHover(node)
    }
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(36.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(if (selected(node)) Color(0xFF262626) else Color.Transparent)
                .border(1.dp, if (hovered) Color(0xFF262626) else Color.Transparent, RoundedCornerShape(4.dp))
                .hoverable(source)
                .clickable(onClick = { onClick(node) })
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            InspectorIcon(node.inspectorInfo)
            BasicText(
                text = node.obj.name,
                style = TextStyle(
                    color = Color.White,
                ),
            )
        }
        if (node is MutableGroupNode) {
            node.children.forEach { obj ->
                Object(
                    node = obj,
                    selected = selected,
                    onHover = onHover,
                    onClick = onClick,
                    modifier = modifier.padding(start = 8.dp),
                )
            }
        }
    }
}
