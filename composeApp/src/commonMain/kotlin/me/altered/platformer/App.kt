package me.altered.platformer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.altered.platformer.scene.editor.EditorScreen
import me.altered.platformer.scene.editor.EditorScreen2
import me.altered.platformer.scene.main.MainScreen

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = EditorScreen,
    ) {
        composable<MainScreen> {
            MainScreen(
                navigateToEditor = { navController.navigate(EditorScreen) }
            )
        }
        composable<EditorScreen> {
            EditorScreen2(
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
