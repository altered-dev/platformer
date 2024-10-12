package me.altered.platformer.node

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.onKeyEvent
import kotlinx.coroutines.isActive

/**
 * **WARNING!** When using composable state in nodes,
 * pass them as lambdas to prevent recomposition of the entire tree.
 *
 * Example:
 *
 * ```kt
 * // in composable
 * var value by remember { mutableStateOf(1) }
 *
 * ValueNode(
 *     value = { value },
 * )
 *
 * // in node
 *
 * class ValueNode(
 *     value: () -> Int,
 * )
 * ```
 */
@Composable
fun World(
    root: Node,
    modifier: Modifier,
    targetFps: Float = 0.0f,
    targetUps: Float = 60.0f,
) {
    val tree = remember(root) { SceneTree(root) }
    var initialTime by remember { mutableLongStateOf(0L) }
    val focusRequester = FocusRequester()

    LaunchedEffect(tree, targetFps, targetUps) {
        focusRequester.requestFocus()
        initialTime = withFrameMillis { it }
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

    Canvas(
        modifier = modifier
            // FIXME: prevent key event repetition
            .onKeyEvent { tree.onKeyEvent(it) }
            .onFocusChanged { println("focus state: $it") }
            .focusRequester(focusRequester)
            .focusable(),
    ) {
        initialTime // state read to constantly trigger recomposition of the canvas
        tree.draw(this)
    }
}
