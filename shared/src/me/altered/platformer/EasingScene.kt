package me.altered.platformer

import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.graphics.Paint
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.ui.Text
import me.altered.platformer.engine.graphics.drawCircle
import me.altered.platformer.engine.graphics.scale
import me.altered.platformer.engine.ui.padding
import me.altered.platformer.timeline.Easing
import org.jetbrains.skia.Canvas

class EasingScene : CanvasNode("easings") {

    private val texts = easings.mapIndexed { index, (name, easing) ->
        +Text(name, padding = padding(left = 16.0f + (index % 6) * 140.8f, top = 16.0f + (index / 6) * 192.0f))
    }

    private val paint = Paint {
        isAntiAlias = true
        isDither = true
        color = Color.Black
    }

    override fun draw(canvas: Canvas) {
        canvas
            .translate(16.0f, 64.0f)
            .scale(128.0f)

        easings.forEachIndexed { index, (_, easing) ->
            repeat(101) {
                val x = it * 0.01f
                val y = easing.ease(x)
                canvas.drawCircle(x, 1.0f - y, 0.01f, paint)
            }
            canvas.translate(1.1f, 0.0f)
            if (index % 6 == 5) {
                canvas.translate(-6.6f, 1.5f)
            }
        }
    }

    companion object {

        private val easings = mutableListOf(
//            "Linear" to Easing.Linear,
            "SineIn" to Easing.SineIn,
            "SineOut" to Easing.SineOut,
            "SineInOut" to Easing.SineInOut,
            "QuadIn" to Easing.QuadIn,
            "QuadOut" to Easing.QuadOut,
            "QuadInOut" to Easing.QuadInOut,
            "CubicIn" to Easing.CubicIn,
            "CubicOut" to Easing.CubicOut,
            "CubicInOut" to Easing.CubicInOut,
            "QuartIn" to Easing.QuartIn,
            "QuartOut" to Easing.QuartOut,
            "QuartInOut" to Easing.QuartInOut,
            "QuintIn" to Easing.QuintIn,
            "QuintOut" to Easing.QuintOut,
            "QuintInOut" to Easing.QuintInOut,
            "ExpoIn" to Easing.ExpoIn,
            "ExpoOut" to Easing.ExpoOut,
            "ExpoInOut" to Easing.ExpoInOut,
            "CircIn" to Easing.CircIn,
            "CircOut" to Easing.CircOut,
            "CircInOut" to Easing.CircInOut,
            "BackIn" to Easing.BackIn,
            "BackOut" to Easing.BackOut,
            "BackInOut" to Easing.BackInOut,
            "ElasticIn" to Easing.ElasticIn,
            "ElasticOut" to Easing.ElasticOut,
            "ElasticInOut" to Easing.ElasticInOut,
            "BounceIn" to Easing.BounceIn,
            "BounceOut" to Easing.BounceOut,
            "BounceInOut" to Easing.BounceInOut,
        )
    }
}
