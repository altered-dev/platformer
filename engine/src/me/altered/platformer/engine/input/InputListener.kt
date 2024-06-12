package me.altered.platformer.engine.input

import org.jetbrains.skiko.SkiaLayer

expect class InputListener(handler: InputHandler) {

    fun listenSkiaLayer(skiaLayer: SkiaLayer)
}
