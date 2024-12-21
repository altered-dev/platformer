package me.altered.platformer.level.node

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.altered.platformer.engine.node.Node
import me.altered.platformer.level.data.Level
import me.altered.platformer.level.data.MutableLevel
import me.altered.platformer.level.data.draw
import me.altered.platformer.level.data.drawInEditor
import me.altered.platformer.level.data.toComposeBrush

class MutableLevelNode(
    override val level: MutableLevel,
    parent: Node? = null,
) : LevelNode(level, parent) {

    override val objects = level.objects.mapTo(mutableStateListOf()) { it.toMutableObjectNode() }

    override var background: Brush
        get() = level.background.staticValue.toComposeBrush()
        set(_) = Unit

    override fun DrawScope.draw() {
        objects.forEach {
            if (it is ObjectNode.Drawable) it.draw(this)
            if (it is ObjectNode.EditorDrawable) it.drawInEditor(this)
        }
    }

    fun toLevel() = Level(
        name = level.name,
        background = level.background.toExpression(),
        objects = objects.map { it.obj.toObject() },
    )
}
