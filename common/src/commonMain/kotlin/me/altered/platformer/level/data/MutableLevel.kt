package me.altered.platformer.level.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.expression.AnimatedBrushState
import me.altered.platformer.level.node.LevelNode
import me.altered.platformer.level.node.MutableLevelNode

class MutableLevel(
    name: String,
    override val background: AnimatedBrushState = AnimatedBrushState(solid(Color.White)),
    override val objects: MutableList<MutableObject> = mutableStateListOf(),
) : Level(name, background, objects) {

    override var name by mutableStateOf(name)

    fun toLevel() = Level(
        name = name,
        background = background.toExpression(),
        objects = objects.map { it.toObject() },
    )

    override fun toMutableLevel() = this

    override fun toNode() = LevelNode(toLevel())

    override fun toMutableNode() = MutableLevelNode(this)
}
