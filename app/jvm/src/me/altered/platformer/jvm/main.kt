package me.altered.platformer.jvm

import me.altered.platformer.editor.EditorScene
import me.altered.platformer.engine.input.InputListener
import me.altered.platformer.engine.loop.SceneTree
import me.altered.platformer.engine.node.Window
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerProperties
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import javax.swing.SwingUtilities.invokeLater

fun main() {
    val skiaLayer = SkiaLayer(
        properties = SkiaLayerProperties(
            isVsyncEnabled = true,
        )
    )
    val window = Window("very platformer game indeed")
    val tree = SceneTree(window)
    val listener = InputListener(tree)
    tree.scene = EditorScene()
    skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, tree)
    invokeLater {
        listener.listenSkiaLayer(skiaLayer)
        window.attachSkiaLayer(skiaLayer)
        skiaLayer.needRedraw()
        window.setSize(1280, 720)
//        window.width = 1280
//        window.height = 720
//        window.pack()
        window.isVisible = true
    }
}
