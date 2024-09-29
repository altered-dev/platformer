package me.altered.platformer

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import me.altered.platformer.node.ComposeNode
import me.altered.platformer.node.World

@Composable
fun App() {
    World(
        root = TestNode("updates:"),
    )
}

class TestNode(
    private val text: String,
) : ComposeNode("test") {

    private var updateCount by mutableIntStateOf(0)
    private var physicsUpdateCount by mutableIntStateOf(0)

    override fun update(delta: Float) {
        super.update(delta)
        updateCount++
    }

    override fun physicsUpdate(delta: Float) {
        super.physicsUpdate(delta)
        physicsUpdateCount++
    }

    @Composable
    override fun Content(
        content: @Composable () -> Unit,
    ) {
        Column {
            Text("$text $updateCount")
            Text("$text $physicsUpdateCount")
            content()
        }
    }
}