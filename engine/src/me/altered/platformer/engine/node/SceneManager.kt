package me.altered.platformer.engine.node

import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.util.currentTimeMillis
import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.SkikoRenderDelegate

/**
 * FIXME: provide a more convenient way to change scenes from nodes
 *
 * TODO: also unsingleton this
 */
object SceneManager : SkikoRenderDelegate {

    var scene: Node = EmptyNode
        set(value) {
            destroy(field)
            field = value
            ready(value)
            prettyPrint(value)
        }

    private val deferred = mutableListOf<() -> Unit>()

    var targetFps = 165.0f
    var targetUps = 60.0f

    private var initialTime = currentTimeMillis()
    private val timeU = 1000.0f / targetUps
    private val timeR = if (targetFps > 0) 1000.0f / targetFps else 0.0f
    private var updateTime = initialTime
    private var frameTime = initialTime
    private var deltaUpdate = 0.0f
    private var deltaFps = 0.0f

    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
        val now = currentTimeMillis()
        deltaUpdate += (now - initialTime) / timeU
        deltaFps += (now - initialTime) / timeR

        if (deltaUpdate >= 1) {
            physicsUpdate((now - updateTime) * 0.001f)
            updateTime = now
            deltaUpdate--
        }

        if (targetFps <= 0 || deltaFps >= 1) {
            update((now - frameTime) * 0.001f)
            frameTime = now
            draw(canvas, width.toFloat(), height.toFloat())
            deltaFps--
        }

        postUpdate()
        initialTime = now
    }

    fun Node.defer(block: () -> Unit) {
        deferred += block
    }

    fun ready() = ready(scene)

    private fun ready(node: Node) {
        if (node is ParentNode) {
            node.children.forEach { ready(it) }
        }
        node.ready()
    }

    fun update(delta: Float) = update(delta, scene)

    private fun update(delta: Float, node: Node) {
        node.update(delta)
        if (node is ParentNode) {
            node.children.forEach { update(delta, it) }
        }
    }

    fun physicsUpdate(delta: Float) = physicsUpdate(delta, scene)

    private fun physicsUpdate(delta: Float, node: Node) {
        node.physicsUpdate(delta)
        if (node is ParentNode) {
            node.children.forEach { physicsUpdate(delta, it) }
        }
    }

    private fun postUpdate() {
        deferred.forEach { it() }
        deferred.clear()
    }

    fun draw(canvas: Canvas, width: Float, height: Float) = draw(canvas, width, height, scene)

    private fun draw(canvas: Canvas, width: Float, height: Float, node: Node) {
        canvas.save()
        node.draw(canvas, width, height)
        canvas.restore()
        if (node is ParentNode) {
            node.children.forEach { draw(canvas, width, height, it) }
        }
    }

    fun input(event: InputEvent) = input(event, scene)

    private fun input(event: InputEvent, node: Node) {
        if (node is ParentNode) {
            node.children.forEach { input(event, it) }
        }
        node.input(event)
    }

    fun destroy() = destroy(scene)

    private fun destroy(node: Node) {
        if (node is ParentNode) {
            node.children.forEach { destroy(it) }
        }
        node.destroy()
    }
}
