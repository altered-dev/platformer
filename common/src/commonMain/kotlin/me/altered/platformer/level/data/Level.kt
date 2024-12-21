package me.altered.platformer.level.data

import androidx.compose.runtime.mutableStateListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.level.node.LevelNode
import me.altered.platformer.level.node.MutableLevelNode

@Serializable
@SerialName("level")
open class Level(
    open val name: String,
    open val objects: List<Object> = emptyList(),
) {

    open fun toMutableLevel() = MutableLevel(
        name = name,
        objects = objects.mapTo(mutableStateListOf()) { it.toMutableObject() },
    )

    open fun toNode() = LevelNode(this)

    open fun toMutableNode() = MutableLevelNode(toMutableLevel())
}

fun Level.ensureImmutable() = when (this) {
    is MutableLevel -> toLevel()
    else -> this
}
