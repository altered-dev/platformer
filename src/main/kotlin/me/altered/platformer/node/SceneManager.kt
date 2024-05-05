package me.altered.platformer.node

import io.github.humbleui.skija.Canvas
import me.altered.platformer.glfw.InputHandler
import me.altered.platformer.glfw.enumValueOf
import me.altered.platformer.glfw.input.InputEvent
import me.altered.platformer.glfw.input.KeyMod

object SceneManager : InputHandler {

    private var currentScene: Node = EmptyNode

    fun setScene(scene: Node) {
        currentScene._destroy()
        currentScene = scene
        scene._ready()
        prettyPrint(scene)
    }

    fun update(delta: Float) {
        currentScene._update(delta)
    }

    fun physicsUpdate(delta: Float) {
        currentScene._physicsUpdate(delta)
    }

    fun input(event: InputEvent) {
        currentScene._input(event)
    }

    fun draw(canvas: Canvas) {
        currentScene._draw(canvas)
    }

    fun destroyScene() {
        currentScene._destroy()
        currentScene = EmptyNode
    }

    override fun onResize(width: Int, height: Int) {
        input(InputEvent.WindowResize(width.toFloat(), height.toFloat()))
    }

    override fun onKey(key: Int, scancode: Int, action: Int, mods: Int) {
        input(InputEvent.Key(enumValueOf(key), scancode, enumValueOf(action), KeyMod(mods)))
    }

    override fun onMouseButton(button: Int, action: Int, mods: Int) {
        input(InputEvent.MouseButton(enumValueOf(button), enumValueOf(action), KeyMod(mods)))
    }

    override fun onCursorMove(x: Double, y: Double) {
        input(InputEvent.CursorMove(x.toFloat(), y.toFloat()))
    }

    override fun onCursorEnter(entered: Boolean) {
        input(InputEvent.CursorEnter(entered))
    }

    override fun onScroll(dx: Double, dy: Double) {
        input(InputEvent.Scroll(dx.toFloat(), dy.toFloat()))
    }
}
