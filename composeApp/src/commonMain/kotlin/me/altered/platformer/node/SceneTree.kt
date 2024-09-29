package me.altered.platformer.node

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
        }

    init {
        root.tree = this
    }

    fun update(delta: Float) = update(delta, root)

    fun physicsUpdate(delta: Float) = physicsUpdate(delta, root)

    @Composable
    fun Content(
        modifier: Modifier = Modifier,
    ) {
        Box(
            modifier = modifier,
        ) {
            Content(root)
        }
    }

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

    @Composable
    private fun Content(node: Node) {
        if (node is ComposeNode) {
            node.Content {
                node.children.forEach { Content(it) }
            }
        } else {
            node.children.forEach { Content(it) }
        }
    }
}
