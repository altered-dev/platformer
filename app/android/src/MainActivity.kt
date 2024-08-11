package me.altered.platformer.android

import android.app.Activity
import android.os.Bundle
import android.widget.FrameLayout
import me.altered.platformer.engine.loop.SceneTree
import me.altered.platformer.engine.node.Window
import org.jetbrains.skiko.SkiaLayer

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = FrameLayout(this)
        layout.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)

        val skiaLayer = SkiaLayer()
        val window = Window("Hello, world!", viewportSize = 20.0f, frameLayout = layout)
        val sceneTree = SceneTree(window)
        skiaLayer.renderDelegate = sceneTree
        skiaLayer.attachTo(layout)

        setContentView(layout, layout.layoutParams)
    }
}
