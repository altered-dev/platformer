package me.altered.platformer.scene.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import me.altered.platformer.level.ObjectContainer
import me.altered.platformer.level.node.ObjectNode

class MutableLevelState(
    name: String,
    objects: Collection<ObjectNode<*>>,
) : ObjectContainer {

    var name by mutableStateOf(name)
    val objects = objects.toMutableStateList()

    override fun place(node: ObjectNode<*>) {
        objects += node
    }

    override fun remove(node: ObjectNode<*>) {
        objects -= node
    }
}

@Composable
fun rememberMutableLevelState(
    name: String,
    objects: Collection<ObjectNode<*>> = emptyList(),
): MutableLevelState = remember {
    MutableLevelState(name, objects)
}
