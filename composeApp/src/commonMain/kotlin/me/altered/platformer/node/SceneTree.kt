package me.altered.platformer.node

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.key.KeyEvent
import me.altered.platformer.graphics.transform
import me.altered.platformer.level.World

class SceneTree(
    root: Node,
) {

    /**
     * The topmost node in the tree.
     *
     * TODO: handle changing root mid-update
     */
    var root: Node = root
        set(value) {
            field.tree = null
            field = value
            value.tree = this
            ready(value)
        }

    init {
        root.tree = this
        ready(root)
        println("tree created, root = $root")
    }

    internal fun update(delta: Float) = update(delta, root)

    internal fun physicsUpdate(delta: Float) = physicsUpdate(delta, root)

    internal fun onKeyEvent(event: KeyEvent) = onKeyEvent(event, root)

    internal fun draw(scope: DrawScope) = scope.draw(root)

    private fun ready(node: Node) {
        node.children.forEach { ready(it) }
        node.ready()
    }

    private fun update(delta: Float, node: Node) {
        node.update(delta)
        node.children.forEach { update(delta, it) }
    }

    private fun physicsUpdate(delta: Float, node: Node) {
        node.physicsUpdate(delta)
        node.children.forEach { physicsUpdate(delta, it) }
    }

    private fun onKeyEvent(event: KeyEvent, node: Node): Boolean {
        return node.children.any { onKeyEvent(event, it) }
            || node.onKeyEvent(event)
    }

    private fun DrawScope.draw(node: Node) {
        withTransform({
            when (node) {
                is Node2D -> transform(node)
                is World -> transform(node)
            }
        }) {
            if (node is CanvasNode) {
                with(node) { draw() }
            }
            node.children.forEach {
                draw(it)
            }
        }
    }
}
