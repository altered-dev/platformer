package me.altered.platformer

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import kotlinx.io.files.Path
import me.altered.platformer.engine.logger.AndroidLogger
import me.altered.platformer.engine.logger.Logger
import me.altered.platformer.level.data.repository.LevelRepositoryImpl

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.defaultLogger = AndroidLogger()
        setContent {
            App(
                repository = remember {
                    LevelRepositoryImpl(
                        localLevelsDirectory = Path(filesDir.path, "levels"),
                    )
                },
                exit = ::finish,
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.systemBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}
