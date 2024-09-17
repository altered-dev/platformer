package me.altered.platformer.engine.node

import me.altered.koml.Vector2f
import me.altered.platformer.engine.graphics.Color
import org.jetbrains.skiko.SkiaLayer

/**
 * TODO: perhaps move the constructor declaration to the platform side,
 * as it is not being used in common code and requires different dependencies on different platforms.
 *
 * The node that controls the game's window. Usually is the tree root above the scene.
 */
expect class Window : Viewport {

    override val window: Window

    /**
     * Whether the window is shown on the screen currently.
     */
    var isVisible: Boolean

    /**
     * The width of the window in pixels. May differ depending on the platform
     *
     * TODO: fix the difference on the platforms
     */
    var width: Int

    /**
     * The height of the window in pixels. May differ depending on the platform
     *
     * TODO: fix the difference on the platforms
     */
    var height: Int

    /**
     * The clear color of the canvas.
     */
    var background: Color

    /**
     * Provides the Skia layer to the platform's application framework.
     */
    fun attachSkiaLayer(layer: SkiaLayer)
}
