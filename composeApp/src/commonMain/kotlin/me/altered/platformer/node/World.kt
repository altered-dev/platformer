package me.altered.platformer.node

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import kotlinx.coroutines.isActive

@Composable
fun World(
    root: Node,
    modifier: Modifier = Modifier,
    targetFps: Float = 165.0f,
    targetUps: Float = 60.0f,
) {
    val tree = remember(root) { SceneTree(root) }

    LaunchedEffect(targetFps, targetUps) {
        var initialTime = withFrameMillis { it }
        val timeU = 1000.0f / targetUps
        val timeR = if (targetFps > 0) 1000.0f / targetFps else 0.0f
        var updateTime = initialTime
        var frameTime = initialTime
        var deltaUpdate = 0.0f
        var deltaFps = 0.0f

        while (coroutineContext.isActive) withFrameMillis { now ->
            deltaUpdate += (now - initialTime) / timeU
            deltaFps += (now - initialTime) / timeR

            if (deltaUpdate >= 1) {
                tree.physicsUpdate((now - updateTime) * 0.001f)
                updateTime = now
                deltaUpdate--
            }

            if (targetFps <= 0 || deltaFps >= 1) {
                tree.update((now - frameTime) * 0.001f)
                frameTime = now
                // draw was here
                deltaFps--
            }

            initialTime = now
        }
    }

    // TODO: scaling
    tree.Content()
}