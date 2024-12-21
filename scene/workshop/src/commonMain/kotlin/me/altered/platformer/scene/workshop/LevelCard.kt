package me.altered.platformer.scene.workshop

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import me.altered.platformer.ui.OutlinedButton
import me.altered.platformer.ui.Text

@Composable
internal fun LevelCard(
    name: String,
    modifier: Modifier = Modifier,
    onPlayClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .height(128.dp)
            .border(1.dp, Color(0xFF262626), RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(4.dp))
            .padding(16.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(name)
        Spacer(modifier = Modifier.weight(1.0f))
        OutlinedButton(
            onClick = onEditClick,
        ) {
            Text("Edit")
        }
        OutlinedButton(
            onClick = onPlayClick,
        ) {
            Text("Play")
        }
    }
}
