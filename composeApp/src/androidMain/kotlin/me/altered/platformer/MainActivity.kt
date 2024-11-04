package me.altered.platformer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.altered.platformer.engine.logger.AndroidLogger
import me.altered.platformer.engine.logger.Logger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.defaultLogger = AndroidLogger()
        setContent { App() }
    }
}
