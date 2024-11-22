package me.altered.platformer.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SelectorRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .border(1.dp, Color(0xFF262626), RoundedCornerShape(4.dp)),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = content,
    )
}

@Composable
fun SelectorButton(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable BoxScope.() -> Unit,
) {
    val color by animateColorAsState(
        targetValue = if (selected) Color(0xFF333333) else Color(0xFFCCCCCC),
    )
    val bg by animateColorAsState(
        targetValue = if (selected) Color(0xFFB2FFB2) else Color.Transparent,
    )
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .background(bg),
        contentAlignment = Alignment.Center,
        content = {
            CompositionLocalProvider(
                LocalContentColor provides color,
            ) {
                icon()
            }
        },
    )
}
