package me.altered.platformer

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "very platformer game indeed",
    ) {
        App()
    }
}
