package me.altered.platformer.level.data

import androidx.compose.runtime.mutableStateListOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.expression.Expression
import me.altered.platformer.expression.InspectorInfo
import me.altered.platformer.expression.const
import me.altered.platformer.expression.toAnimatedBrushState
import me.altered.platformer.level.node.LevelNode
import me.altered.platformer.level.node.MutableLevelNode

@Serializable
@SerialName("level")
open class Level(
    open val name: String,
    open val background: Expression<Brush> = const(solid(0xFF333333)),
    open val camera: Camera = Camera(),
    open val objects: List<Object> = emptyList(),
) {

    open fun toMutableLevel() = MutableLevel(
        name = name,
        background = background.toAnimatedBrushState(InspectorInfo.Background),
        camera = camera.toMutableObject(),
        objects = objects.mapTo(mutableStateListOf()) { it.toMutableObject() },
    )

    open fun toNode() = LevelNode(this)

    open fun toMutableNode() = MutableLevelNode(toMutableLevel())
}

fun Level.ensureImmutable() = when (this) {
    is MutableLevel -> toLevel()
    else -> this
}
