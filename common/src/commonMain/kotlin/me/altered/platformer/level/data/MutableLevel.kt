package me.altered.platformer.level.data

import androidx.compose.runtime.mutableStateListOf
import me.altered.platformer.level.node.LevelNode
import me.altered.platformer.level.node.MutableLevelNode

class MutableLevel(
    name: String,
    override val objects: MutableList<MutableObject> = mutableStateListOf(),
) : Level(name, objects) {

    fun toLevel() = Level(
        name = name,
        objects = objects.map { it.toObject() },
    )

    override fun toMutableLevel() = this

    override fun toNode() = LevelNode(toLevel())

    override fun toMutableNode() = MutableLevelNode(this)
}
