package me.altered.platformer.jvm

import me.altered.platformer.MainScene
import me.altered.platformer.engine.input.InputListener
import me.altered.platformer.engine.loop.SceneTree
import me.altered.platformer.engine.node.Window
import me.altered.platformer.engine.util.Logger
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import javax.swing.SwingUtilities.invokeLater

fun main() {
    val window = Window("Hello, world!", null, 1280, 720)
    Logger.d("main", "created window: $window")
    val tree = SceneTree(window)
    val listener = InputListener(tree)
    val skiaLayer = SkiaLayer()
    skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, tree)
    listener.listenSkiaLayer(skiaLayer)
    window.attachSkiaLayer(skiaLayer)
    Logger.d("main", "skiaLayer: $skiaLayer")
    tree.currentScene = MainScene()
    window.pack()
    window.isVisible = true
    invokeLater { skiaLayer.needRedraw() }
}
