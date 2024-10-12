package me.altered.platformer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import me.altered.platformer.node.World

@Composable
fun App() {
    val textMeasurer = rememberTextMeasurer()
    World(
        root = remember { MainScene(textMeasurer) },
        modifier = Modifier
            .fillMaxSize(),
    )
}
