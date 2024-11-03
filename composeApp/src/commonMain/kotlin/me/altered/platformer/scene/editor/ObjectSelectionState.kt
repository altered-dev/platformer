package me.altered.platformer.scene.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

class ObjectSelectionState(
    initial: List<ULong>,
) {

    // probably replace with object references
    val selection = mutableStateListOf(*initial.toTypedArray())

    fun selectSingle(id: ULong) {
        selection.clear()
        selection += id
    }

    fun deselect() {
        selection.clear()
    }
}

@Composable
fun rememberObjectSelectionState(
    initial: List<ULong> = emptyList(),
) = remember { ObjectSelectionState(initial) }
