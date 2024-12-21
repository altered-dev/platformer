package me.altered.platformer.scene.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import me.altered.platformer.ui.OutlinedButton
import me.altered.platformer.ui.Text

@Serializable
data object MenuScreen

@Composable
fun MenuScreen(
    onStoryClick: () -> Unit = {},
    onWorkshopClick : () -> Unit = {},
    onSettingsClick : () -> Unit = {},
    onExitClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF333333))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, BiasAlignment.Vertical(-0.25f)),
        horizontalAlignment = Alignment.Start,
    ) {
        BasicText(
            text = "very cool platformer game",
            style = TextStyle(
                color = Color(0xFFE6E6E6),
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
            ),
        )

        OutlinedButton(
            onClick = onStoryClick,
            modifier = Modifier.width(256.dp),
            enabled = false,
        ) {
            Text("Story (WIP)")
        }

        OutlinedButton(
            onClick = onWorkshopClick,
            modifier = Modifier.width(256.dp),
        ) {
            Text("Workshop")
        }

        OutlinedButton(
            onClick = onSettingsClick,
            modifier = Modifier.width(256.dp),
        ) {
            Text("Settings")
        }

        OutlinedButton(
            onClick = onExitClick,
            modifier = Modifier.width(256.dp),
        ) {
            Text("Exit")
        }
    }
}
