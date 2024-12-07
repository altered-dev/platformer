package me.altered.platformer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import me.altered.platformer.scene.editor.ui.EditorScreen
import me.altered.platformer.scene.main.LevelScreen

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LevelScreen("test_level"),
    ) {
        composable<LevelScreen> {
            val name = it.toRoute<LevelScreen>().name
            LevelScreen(
                name = name,
                navigateToEditor = { navController.navigate(EditorScreen(name)) }
            )
        }
        composable<EditorScreen> {
            val name = it.toRoute<EditorScreen>().name
            EditorScreen(
                name = name,
                onBackClick = { navController.popBackStack() },
                onPlayClick = { navController.navigate(LevelScreen(name)) {
                    launchSingleTop = true
                    popUpTo<EditorScreen>()
                } }
            )
        }
    }
}
