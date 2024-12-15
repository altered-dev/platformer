package me.altered.platformer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Separator() {
    Spacer(
        modifier = Modifier
            .size(1.dp, 20.dp)
            .background(Color(0xFF262626)),
    )
}
