package me.altered.platformer.level.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import me.altered.platformer.expression.AnimatedBrushState
import me.altered.platformer.expression.InspectorInfo
import me.altered.platformer.level.node.LevelNode
import me.altered.platformer.level.node.MutableLevelNode

class MutableLevel(
    name: String,
    override val background: AnimatedBrushState = AnimatedBrushState(solid(0xFF333333), InspectorInfo.Background),
    override val camera: MutableCamera = MutableCamera(),
    override val objects: SnapshotStateList<MutableObject> = mutableStateListOf(),
) : Level(name, background, camera, objects) {

    override var name by mutableStateOf(name)

    fun toLevel() = Level(
        name = name,
        background = background.toExpression(),
        camera = camera.toObject(),
        objects = objects.map { it.toObject() },
    )

    override fun toMutableLevel() = this

    override fun toNode() = LevelNode(toLevel())

    override fun toMutableNode() = MutableLevelNode(this)
}
