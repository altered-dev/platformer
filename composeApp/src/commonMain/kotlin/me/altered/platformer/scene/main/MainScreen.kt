package me.altered.platformer.scene.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.rememberTextMeasurer
import kotlinx.serialization.Serializable
import me.altered.platformer.engine.node.World

@Serializable
data object MainScreen

@Composable
fun MainScreen(
    navigateToEditor: () -> Unit = {},
) {
    val textMeasurer = rememberTextMeasurer()
    World(
        root = remember { MainScene(textMeasurer) },
        modifier = Modifier
            .fillMaxSize()
            .onKeyEvent {
                if (it.type == KeyEventType.KeyDown && it.key == Key.E) {
                    navigateToEditor()
                    true
                } else false
            },
    )
}
