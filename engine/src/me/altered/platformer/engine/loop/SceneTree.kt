package me.altered.platformer.engine.loop

import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.InputHandler
import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.node.Viewport
import me.altered.platformer.engine.node.Window
import me.altered.platformer.engine.node.prettyPrint
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.ui.UiNode
import me.altered.platformer.engine.util.currentTimeMillis
import me.altered.platformer.engine.graphics.transform
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Rect
import org.jetbrains.skiko.SkikoRenderDelegate

open class SceneTree(
    val root: Window,
) : SkikoRenderDelegate, InputHandler {

    init {
        @Suppress("LeakingThis")
        root.tree = this
    }

    var scene: Node? = null
        set(value) {
            // TODO: enterTree, exitTree
            field?.parent = null
            field?.tree = null

            field = value
            if (value == null) return

            value.parent = root
            value.tree = this
            prettyPrint(value)
            ready(value)
        }

    var targetFps = 165.0f
    var targetUps = 60.0f
    var isDebug = true

    private var initialTime = currentTimeMillis()
    private val timeU = 1000.0f / targetUps
    private val timeR = if (targetFps > 0) 1000.0f / targetFps else 0.0f
    private var updateTime = initialTime
    private var frameTime = initialTime
    private var deltaUpdate = 0.0f
    private var deltaFps = 0.0f
    private var currentBounds = Rect(0.0f, 0.0f, 0.0f, 0.0f)

    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
        val now = currentTimeMillis()
        val scene = root
        deltaUpdate += (now - initialTime) / timeU
        deltaFps += (now - initialTime) / timeR

        if (deltaUpdate >= 1) {
            physicsUpdate((now - updateTime) * 0.001f, scene)
            updateTime = now
            deltaUpdate--
        }

        if (targetFps <= 0 || deltaFps >= 1) {
            update((now - frameTime) * 0.001f, scene)
            frameTime = now
            canvas.clear(0xFFFFFFFF.toInt())
            val bounds = Rect.makeWH(width.toFloat(), height.toFloat())
            draw(canvas, bounds, scene)
            currentBounds = bounds
            deltaFps--
        }

        initialTime = now
    }

    private fun ready(node: Node) {
        node.children.forEach { ready(it) }
        node.ready()
    }

    private fun update(delta: Float, node: Node?) {
        if (node == null) return
        node.update(delta)
        node.children.forEach { update(delta, it) }
    }

    private fun physicsUpdate(delta: Float, node: Node?) {
        if (node == null) return
        node.physicsUpdate(delta)
        node.children.forEach { physicsUpdate(delta, it) }
    }

    private fun draw(canvas: Canvas, bounds: Rect, node: Node?) {
        if (node == null) return
        canvas.save()
        when (node) {
            is Viewport -> canvas.transform(node)
            is Node2D -> canvas.transform(node)
            is UiNode -> {
                if (node.needsLayout || (bounds != currentBounds && node.parent !is UiNode)) {
                    node.layout(bounds)
                }
            }
        }

        canvas.save()
        if (node is CanvasNode) {
            node.draw(canvas)
            if (isDebug) {
                node.debugDraw(canvas)
            }
        }
        canvas.restore()

        node.children.forEach { draw(canvas, bounds, it) }
        canvas.restore()
    }

    override fun input(event: InputEvent) = input(event, scene)

    private fun input(event: InputEvent, node: Node?) {
        if (node == null) return
        node.children.forEach { input(event, it) }
        node.input(event)
    }

    fun destroy() = destroy(scene)

    private fun destroy(node: Node?) {
        if (node == null) return
        node.children.forEach { destroy(it) }
        node.destroy()
    }
}
