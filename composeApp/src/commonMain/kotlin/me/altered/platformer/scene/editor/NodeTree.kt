package me.altered.platformer.scene.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Icon
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
import me.altered.platformer.Res
import me.altered.platformer.circle
import me.altered.platformer.level.objects.MutableEllipse
import me.altered.platformer.level.objects.MutableLevel
import me.altered.platformer.level.objects.MutableObject
import me.altered.platformer.level.objects.MutableRectangle
import me.altered.platformer.rectangle
import org.jetbrains.compose.resources.painterResource

@Composable
fun NodeTree(
    level: MutableLevel,
    selectionState: SelectionState,
) {
    val source = remember { MutableInteractionSource() }
    val hovered by source.collectIsHoveredAsState()
    LaunchedEffect(hovered) {
        if (hovered) selectionState.hovered2 = null
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
        items(level.objects, key = { it.id }) {
            Object(
                obj = it,
                selected = it in selectionState.selection2,
                onHover = { selectionState.hovered2 = it },
                onClick = { selectionState.selectSingle(it) },
            )
        }
    }
}

@Composable
private fun Object(
    obj: MutableObject,
    selected: Boolean,
    onHover: () -> Unit,
    onClick: () -> Unit,
) {
    val source = remember { MutableInteractionSource() }
    val hovered by source.collectIsHoveredAsState()
    LaunchedEffect(hovered) {
        if (hovered) onHover()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(if (selected) Color(0xFF262626) else Color.Transparent)
            .border(1.dp, if (hovered) Color(0xFF262626) else Color.Transparent, RoundedCornerShape(4.dp))
            .hoverable(source)
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(
            painter = when (obj) {
                is MutableRectangle -> painterResource(Res.drawable.rectangle)
                is MutableEllipse -> painterResource(Res.drawable.circle)
            },
            contentDescription = null,
            tint = Color(0xFFCCCCCC),
        )
        BasicText(
            text = obj.name,
            style = TextStyle(
                color = Color.White,
            ),
        )
    }
}
