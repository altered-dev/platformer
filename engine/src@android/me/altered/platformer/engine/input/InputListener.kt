package me.altered.platformer.engine.input

import org.jetbrains.skiko.SkiaLayer

actual class InputListener actual constructor(
    private val handler: InputHandler,
) {

    actual fun listenSkiaLayer(skiaLayer: SkiaLayer) {

    }
}
