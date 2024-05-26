package me.altered.platformer.android

import android.app.Activity
import android.os.Bundle
import android.widget.FrameLayout
import me.altered.platformer.engine.node.SceneManager
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = FrameLayout(this)
        layout.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)

        val skiaLayer = SkiaLayer()
        val renderDelegate = SceneManager
        skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, renderDelegate)
        skiaLayer.attachTo(layout)

        setContentView(layout, layout.layoutParams)
    }
}
