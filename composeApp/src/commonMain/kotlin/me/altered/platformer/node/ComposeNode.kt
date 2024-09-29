package me.altered.platformer.node

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable

open class ComposeNode(
    name: String = "ComposeNode",
    parent: Node? = null,
) : Node(name, parent) {

    @Composable
    open fun Content(
        content: @Composable () -> Unit,
    ) {
        Box { content() }
    }

    // overload because open composable functions
    // with default values are not supported
    @Composable
    fun Content() = Content {  }
}
