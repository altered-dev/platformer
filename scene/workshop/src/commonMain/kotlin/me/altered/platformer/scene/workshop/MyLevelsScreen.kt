package me.altered.platformer.scene.workshop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import me.altered.platformer.level.data.repository.LevelRepository
import me.altered.platformer.resources.Res
import me.altered.platformer.resources.add
import me.altered.platformer.resources.back
import me.altered.platformer.ui.Icon
import me.altered.platformer.ui.IconButton
import me.altered.platformer.ui.OutlinedButton
import me.altered.platformer.ui.Text
import org.jetbrains.compose.resources.painterResource

@Serializable
data object MyLevelsScreen

@Composable
fun MyLevelsScreen(
    repository: LevelRepository,
    onPlayClick: (String) -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onNewLevelClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val levels = remember { mutableStateListOf<String>() }
    LaunchedEffect(Unit) {
        val newLevels = repository.list()
        levels.clear()
        levels.addAll(newLevels)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF333333)),
    ) {
        Row(
            modifier = Modifier
                .height(72.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(painterResource(Res.drawable.back))
            }
            Text("My levels")
            Spacer(modifier = Modifier.weight(1.0f))
            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        val name = repository.create()
                        onNewLevelClick(name)
                    }
                },
            ) {
                Icon(painterResource(Res.drawable.add))
                Text("New level")
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(256.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(levels, key = { it }) { level ->
                LevelCard(
                    name = level,
                    modifier = Modifier.fillMaxWidth(),
                    onPlayClick = { onPlayClick(level) },
                    onEditClick = { onEditClick(level) },
                )
            }
        }
    }
}
