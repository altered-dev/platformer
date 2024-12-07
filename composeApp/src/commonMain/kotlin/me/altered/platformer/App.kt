package me.altered.platformer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.altered.platformer.scene.editor.EditorScreen
import me.altered.platformer.scene.main.MainScreen
import me.altered.platformer.scene.menu.MenuScreen
import me.altered.platformer.scene.myLevels.MyLevelsScreen
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
                navigateToEditor = { navController.navigate(EditorScreen) },
                navigateToMain = { navController.navigate(MainScreen) },
                navigateToSettings = { navController.navigate(SettingsScreen) },
                navigateToShop = { navController.navigate(ShopScreen) },
                navigateToMyLevels = { navController.navigate(MyLevelsScreen) }
            )
        }
        composable<MainScreen> {
            MainScreen(
                navigateToEditor = { navController.navigate(EditorScreen) },
                navigateBack = { navController.popBackStack()}
            )
        }
        composable<EditorScreen> {
            EditorScreen(
                onBackClick = { navController.popBackStack() },
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
            MyLevelsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
