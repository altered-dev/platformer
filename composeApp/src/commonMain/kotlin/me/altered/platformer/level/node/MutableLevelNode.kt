package me.altered.platformer.level.node

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.altered.platformer.engine.node.Node
import me.altered.platformer.level.objects.draw
import me.altered.platformer.level.objects.drawInEditor

class MutableLevelNode(
    name: String,
    parent: Node? = null,
    // this can lead to way too many conversions
    objects: List<ObjectNode> = emptyList(),
) : LevelNode(name, parent, objects) {

    override val objects = objects.mapTo(mutableStateListOf()) { it.toMutableObjectNode() }

    override fun DrawScope.draw() {
        objects.forEach {
            if (it is ObjectNode.Drawable) it.draw(this)
            if (it is ObjectNode.EditorDrawable) it.drawInEditor(this)
        }
    }
}
