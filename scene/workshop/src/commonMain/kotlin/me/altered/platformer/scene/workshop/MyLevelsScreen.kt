package me.altered.platformer.scene.workshop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.Serializable
import me.altered.platformer.ui.CustomButton

@Serializable
data object MyLevelsScreen

@Composable
fun MyLevelsScreen(
    levels: List<String>,
    onLevelClick: (String) -> Unit,
    onAddNewLevelClick: (String) -> Unit,
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        levels.forEach { levelName ->
            CustomButton(
                text = levelName,
                onClick = { onLevelClick(levelName) },
                modifier = Modifier.padding(8.dp)
            )
        }

        CustomButton(
            text = "Create a new Level",
            onClick = {
                val nextLevelName = "level${levels.size + 1}"
                onAddNewLevelClick(nextLevelName)
            },
            modifier = Modifier.padding(8.dp)
        )

        CustomButton(
            text = "Back",
            onClick = onBackClick,
            modifier = Modifier.padding(8.dp)
        )
    }
}

fun getSavedLevels(): List<String> {
    val directoryPath = Path("levels")
    val fileSystem = SystemFileSystem

    return if (fileSystem.exists(directoryPath)) {
        fileSystem.list(directoryPath)
            .filter { it.name.endsWith(".level") }
            .map { it.name.removeSuffix(".level") }
    } else {
        emptyList()
    }
}
