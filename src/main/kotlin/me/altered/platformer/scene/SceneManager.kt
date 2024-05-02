package me.altered.platformer.scene

import io.github.humbleui.skija.Canvas
import me.altered.platformer.glfw.input.InputEvent

object SceneManager {

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
}
