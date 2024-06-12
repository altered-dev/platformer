package me.altered.platformer.engine.node

import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.InputHandler
import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.SkikoRenderDelegate

@Deprecated("Replace with SceneTree.", ReplaceWith("SceneTree()", "me.altered.platformer.engine.loop.SceneTree"), DeprecationLevel.ERROR)
object SceneManager : SkikoRenderDelegate, InputHandler {

    var scene: Node = EmptyNode
        set(value) {
            error("Replace with SceneTree.")
        }

    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
        error("Replace with SceneTree.")
    }

    override fun input(event: InputEvent) = input(event, scene)

    private fun input(event: InputEvent, node: Node) {
        error("Replace with SceneTree.")
    }
}
