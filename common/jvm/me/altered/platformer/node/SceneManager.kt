package me.altered.platformer.node

import org.jetbrains.skia.Canvas
import me.altered.platformer.glfw.window.InputHandler
import me.altered.platformer.glfw.enumValueOf
import me.altered.platformer.glfw.input.InputEvent
import me.altered.platformer.glfw.input.KeyMod

object SceneManager : InputHandler {

    var scene: Node = EmptyNode
        set(value) {
            destroy(field)
            field = value
            ready(value)
            prettyPrint(value)
        }

    private val deferred = mutableListOf<() -> Unit>()

    fun Node.defer(block: () -> Unit) {
        deferred += block
    }

    fun ready() = ready(scene)

    private fun ready(node: Node) {
        if (node is ParentNode) node.children.forEach {
            ready(it)
        }
        node.ready()
    }

    fun update(delta: Float) = update(delta, scene)

    private fun update(delta: Float, node: Node) {
        node.update(delta)
        if (node is ParentNode) node.children.forEach {
            update(delta, it)
        }
    }

    fun physicsUpdate(delta: Float) = physicsUpdate(delta, scene)

    private fun physicsUpdate(delta: Float, node: Node) {
        node.physicsUpdate(delta)
        if (node is ParentNode) node.children.forEach {
            physicsUpdate(delta, it)
        }
    }

    fun input(event: InputEvent) = input(event, scene)


    private fun input(event: InputEvent, node: Node): Boolean {
        if (node is ParentNode && node.children.any { input(event, it) }) return true
        return node.input(event)
    }

    fun draw(canvas: Canvas) = draw(canvas, scene)

    private fun draw(canvas: Canvas, node: Node) {
        canvas.save()
        node.draw(canvas)
        canvas.restore()
        if (node is ParentNode) node.children.forEach {
            draw(canvas, it)
        }
    }

    fun postUpdate() {
        deferred.forEach { it() }
        deferred.clear()
    }

    fun destroy() = destroy(scene)

    private fun destroy(node: Node) {
        if (node is ParentNode) node.children.forEach {
            destroy(it)
        }
        node.destroy()
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
