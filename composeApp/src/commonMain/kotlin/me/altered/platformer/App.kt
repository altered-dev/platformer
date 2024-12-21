package me.altered.platformer

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import me.altered.platformer.level.data.repository.LevelRepositoryImpl
import me.altered.platformer.scene.editor.ui.EditorScreen
import me.altered.platformer.scene.level.LevelScreen
import me.altered.platformer.scene.main.MenuScreen
import me.altered.platformer.scene.workshop.MyLevelsScreen
import me.altered.platformer.scene.settings.SettingsScreen
import me.altered.platformer.scene.shop.ShopScreen

@Composable
fun App(
    exit: () -> Unit = {},
) {
    Spacer(modifier = Modifier.fillMaxSize().background(Color.Black))
    val navController = rememberNavController()
    val repository = remember { LevelRepositoryImpl() }
    NavHost(
        navController = navController,
        startDestination = MenuScreen,
        enterTransition = { fadeIn(tween(300)) },
        exitTransition = { fadeOut(tween(300)) },
    ) {
        composable<MenuScreen> {
            MenuScreen(
                onWorkshopClick = { navController.navigate(MyLevelsScreen) },
                onSettingsClick = { navController.navigate(SettingsScreen) },
                onExitClick = exit,
            )
        }
        composable<LevelScreen> {
            val name = it.toRoute<LevelScreen>().name
            LevelScreen(
                name = name,
                repository = repository,
                navigateToEditor = { navController.navigate(EditorScreen(name)) },
                navigateBack = { navController.popBackStack() },
            )
        }
        composable<EditorScreen> {
            val name = it.toRoute<EditorScreen>().name
            EditorScreen(
                name = name,
                repository = repository,
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
            MyLevelsScreen(
                repository = repository,
                onPlayClick = { name -> navController.navigate(LevelScreen(name)) },
                onEditClick = { name -> navController.navigate(EditorScreen(name)) },
                onNewLevelClick = { name -> navController.navigate(EditorScreen(name)) },
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
