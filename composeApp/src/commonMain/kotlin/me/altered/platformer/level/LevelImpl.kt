package me.altered.platformer.level

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlinx.serialization.Serializable
import me.altered.platformer.level.objects.Object

@Serializable
class LevelImpl(
    override val name: String,
    override val objects: List<Object> = emptyList(),
) : Level {

    override fun DrawScope.draw() {
        objects.forEach {
            if (it is Object.Drawable) {
                with(it) { draw() }
            }
        }
    }

    override fun eval(time: Float) {
        objects.forEach { it.eval(time) }
    }

    override fun toMutableLevel(): MutableLevel = MutableLevelImpl(
        name = name,
        objects = objects.mapTo(mutableStateListOf()) { it.toMutableObject() },
    )
}
