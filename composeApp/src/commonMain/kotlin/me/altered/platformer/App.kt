package me.altered.platformer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import me.altered.platformer.scene.editor.ui.EditorScreen
import me.altered.platformer.scene.level.LevelScreen
import me.altered.platformer.scene.main.MenuScreen
import me.altered.platformer.scene.workshop.MyLevelsScreen
import me.altered.platformer.scene.workshop.getSavedLevels
import me.altered.platformer.scene.settings.SettingsScreen
import me.altered.platformer.scene.shop.ShopScreen

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MenuScreen,
    ) {
        composable<MenuScreen> {
            MenuScreen(
                navigateToEditor = { navController.navigate(EditorScreen("test_level")) },
                navigateToLevel = { navController.navigate(LevelScreen("test_level")) },
                navigateToSettings = { navController.navigate(SettingsScreen) },
                navigateToShop = { navController.navigate(ShopScreen) },
                navigateToMyLevels = { navController.navigate(MyLevelsScreen) }
            )
        }
        composable<LevelScreen> {
            val name = it.toRoute<LevelScreen>().name
            LevelScreen(
                name = name,
                navigateToEditor = { navController.navigate(EditorScreen(name)) },
                navigateBack = { navController.popBackStack()}
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
        composable<SettingsScreen> {
            SettingsScreen(
                onBackClick = { navController.popBackStack() },
            )
        }
        composable<ShopScreen> {
            ShopScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<MyLevelsScreen> {
            val levels = remember { getSavedLevels() }
            MyLevelsScreen(
                levels = levels,
                onLevelClick = { levelName ->
                    navController.navigate(LevelScreen(levelName))
                },
                onAddNewLevelClick = { newLevelName ->
                    navController.navigate(EditorScreen(newLevelName))
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
