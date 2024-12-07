package me.altered.platformer.scene.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import me.altered.platformer.ui.CustomButton

@Serializable
data object MenuScreen

@Composable
fun MenuScreen(
    navigateToMain: () -> Unit,
    navigateToEditor: () -> Unit,
    navigateToSettings : () -> Unit,
    navigateToShop : () -> Unit,
    navigateToMyLevels : () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomButton(
            text = "Main Scene",
            onClick = navigateToMain,
            modifier = Modifier.padding(8.dp)
        )
        CustomButton(
            text = "Editor Scene",
            onClick = navigateToEditor,
            modifier = Modifier.padding(8.dp)
        )
        CustomButton(
            text = "Settings",
            onClick = navigateToSettings,
            modifier = Modifier.padding(8.dp)
        )
        CustomButton(
            text = "Shop",
            onClick = navigateToShop,
            modifier = Modifier.padding(8.dp)
        )
        CustomButton(
            text = "My Levels",
            onClick = navigateToMyLevels,
            modifier = Modifier.padding(8.dp)
        )
    }
}