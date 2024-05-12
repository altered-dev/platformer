package me.altered.platformer

import me.altered.koml.Vector2f
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Key
import me.altered.platformer.engine.input.pressed
import me.altered.platformer.engine.input.released
import me.altered.platformer.engine.io.font
import me.altered.platformer.engine.io.resource
import me.altered.platformer.engine.node.ParentNode
import me.altered.platformer.engine.node.SceneManager
import me.altered.platformer.engine.node.getValue
import me.altered.platformer.engine.node.provideDelegate
import me.altered.platformer.`object`.Ellipse
import me.altered.platformer.`object`.Polygon
import me.altered.platformer.`object`.Rectangle
import me.altered.platformer.player.Player
import me.altered.platformer.skija.buildPaint
import me.altered.platformer.skija.color
import me.altered.platformer.timeline.Easing
import me.altered.platformer.timeline.Timeline
import me.altered.platformer.timeline.animated
import me.altered.platformer.timeline.at
import me.altered.platformer.timeline.const
import me.altered.platformer.timeline.with
import me.altered.platformer.ui.Button
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skia.Rect

class MainScene: ParentNode("main") {

    override val name = "main"

    private val timeline = Timeline()
    private var timeDirection = 0.0f
    private var fps = 0.0f

    private val player by Player(Vector2f(100.0f, 300.0f))

    private val button by Button(
        rect = Rect.makeXYWH(50.0f, 100.0f, 128.0f, 36.0f),
        name = "Go to editor",
        onClick = { SceneManager.scene = EditorScene() },
    )
    private val rectangle by Rectangle(
        x = const(400.0f),
        y = const(200.0f),
        width = timeline.animated(
            timeline.animated(
                20.0f at 0.0f,
                80.0f at 10.0f,
            ) at 0.0f,
            timeline.animated(
                -80.0f at 0.0f,
                -20.0f at 10.0f,
            ) at 10.0f with Easing.expoInOut,
        ),
        height = const(40.0f),
        rotation = timeline.animated(
            0.0f at 0.0f,
            360.0f * 10 at 10.0f with Easing.cubicInOut,
        ),
        fill = const(color(0xFFFF0000)),
        stroke = const(color(Color.BLACK)),
        strokeWidth = const(2.0f),
    )

    private val ellipse by Ellipse(
        x = const(200.0f),
        y = const(400.0f),
        width = timeline.animated(
            timeline.animated(
                20.0f at 0.0f,
                80.0f at 10.0f,
            ) at 0.0f,
            timeline.animated(
                80.0f at 0.0f,
                20.0f at 10.0f,
            ) at 10.0f with Easing.expoInOut,
        ),
        height = const(40.0f),
        rotation = timeline.animated(
            0.0f at 0.0f,
            360.0f * -5 at 10.0f with Easing.cubicInOut,
        ),
        fill = const(color(0xFFFF0000)),
        stroke = const(color(Color.BLACK)),
        strokeWidth = const(2.0f),
    )

    private val polygon by Polygon(
        x = const(335.0f),
        y = const(315.0f),
        rotation = timeline.animated(
            0.0f at 0.0f,
            180.0f at 3.0f with Easing.expoInOut,
        ),

        timeline.animated(
            -35.0f at 0.0f,
            65.0f at 3.0f with Easing.expoInOut,
        ),
        const(-15.0f),

        const(15.0f),
        const(-45.0f),

        timeline.animated(
            35.0f at 0.0f,
            -5.0f at 3.0f with Easing.expoInOut,
        ),
        const(5.0f),

        const(25.0f),
        timeline.animated(
            45.0f at 0.0f,
            -15.0f at 3.0f with Easing.expoInOut,
        ),

        const(5.0f),
        const(45.0f),

        fill = const(color(0xFF00FF00)),
        stroke = const(color(Color.BLACK)),
        strokeWidth = const(2.0f),
    )

    private val paint = buildPaint { color = Color.BLACK }

    override fun update(delta: Float) {
        fps = 1.0f / delta
        timeline.time += timeDirection * delta
    }

    override fun draw(canvas: Canvas, width: Float, height: Float) {
        canvas.drawString("time: ${timeline.time}", 10.0f, 40.0f, font, paint)
        canvas.drawString("fps: $fps", 10.0f, 60.0f, font, paint)
    }

    override fun input(event: InputEvent) {
        when {
            event pressed Key.RIGHT -> timeDirection += 1
            event released Key.RIGHT -> timeDirection -= 1
            event pressed Key.LEFT -> timeDirection -= 1
            event released Key.LEFT -> timeDirection += 1
        }
    }

    companion object {

        private val font = font(resource("fonts/Inter-Regular.ttf"), 13.0f)
    }
}
