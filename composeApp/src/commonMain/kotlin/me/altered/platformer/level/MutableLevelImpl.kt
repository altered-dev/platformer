package me.altered.platformer.level

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.altered.platformer.level.objects.MutableObject
import me.altered.platformer.level.objects.Object

class MutableLevelImpl(
    name: String,
    override val objects: MutableList<MutableObject> = mutableStateListOf(),
) : MutableLevel {

    override var name by mutableStateOf(name)

    override fun DrawScope.draw() {
        objects.forEach {
            if (it is Object.EditorDrawable) {
                with(it) { draw() }
            }
        }
    }

    override fun eval(time: Float) {
        objects.forEach { it.eval(time) }
    }

    override fun toLevel(): Level = LevelImpl(
        name = name,
        objects = objects.map { it.toObject() },
    )
}
