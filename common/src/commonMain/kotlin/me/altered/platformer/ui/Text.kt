package me.altered.platformer.ui

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun Text(
    text: String,
) {
    BasicText(
        text = text,
        style = TextStyle(
            color = Color(0xFFE6E6E6),
        ),
    )
}
