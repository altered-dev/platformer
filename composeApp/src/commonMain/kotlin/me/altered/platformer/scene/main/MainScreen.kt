package me.altered.platformer.scene.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import me.altered.platformer.engine.node.World
import me.altered.platformer.ui.BackButton

@Serializable
data object MainScreen

@Composable
fun MainScreen(
    navigateToEditor: () -> Unit = {},
    navigateBack: () -> Unit = {}
) {
    val textMeasurer = rememberTextMeasurer()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BackButton(
            onClick = navigateBack,
            modifier = Modifier.padding(16.dp)
        )
    }
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
