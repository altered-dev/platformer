package me.altered.platformer.scene.workshop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import me.altered.platformer.level.data.repository.LevelRepository
import me.altered.platformer.ui.CustomButton

@Serializable
data object MyLevelsScreen

@Composable
fun MyLevelsScreen(
    repository: LevelRepository,
    onLevelClick: (String) -> Unit,
    onAddNewLevelClick: (String) -> Unit,
    onBackClick: () -> Unit = {}
) {
    val levels = remember { mutableStateListOf<String>() }
    LaunchedEffect(Unit) {
        val newLevels = repository.list()
        levels.clear()
        levels.addAll(newLevels)
    }
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
