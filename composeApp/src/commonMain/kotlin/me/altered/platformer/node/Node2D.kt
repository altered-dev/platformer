package me.altered.platformer.node

import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

open class Node2D(
    name: String = "Node2D",
    parent: Node? = null,
) : ComposeNode(name, parent) {

    @Composable
    final override fun Content(
        content: @Composable () -> Unit,
    ) {
        Content(
            modifier = Modifier
                .offset(),
            content = content,
        )
    }

    @Composable
    protected open fun Content(
        modifier: Modifier,
        content: @Composable () -> Unit,
    ) {

    }
}
