@file:OptIn(ExperimentalSerializationApi::class)

package me.altered.platformer.level

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import me.altered.platformer.level.node.LevelNode
import me.altered.platformer.level.node.MutableLevelNode
import me.altered.platformer.level.node.toMutableObjectNode
import me.altered.platformer.level.node.toObjectNode
import me.altered.platformer.level.objects.MutableObject
import me.altered.platformer.level.objects.Object

@Serializable
data class Level(
    val name: String,
    val objects: List<Object> = emptyList(),
)

fun Level.toLevelNode(): LevelNode {
    return LevelNode(
        name = name,
        objects = objects.map { it.toObjectNode() },
    )
}

fun Level.toMutableLevelNode(): MutableLevelNode {
    return MutableLevelNode(
        name = name,
        objects = objects.map { it.toMutableObjectNode() },
    )
}

private val cbor = Cbor {
    encodeDefaults = true
    ignoreUnknownKeys = true
}

fun LevelNode.saveToFile() {
    val level = Level(name, objects.map {
        when (val obj = it.obj) {
            is MutableObject -> obj.toObject()
            else -> obj
        }
    })
    level.saveToFile()
}

fun Level.saveToFile() {
    val bytes = cbor.encodeToByteArray(this)
    val directoryPath = Path("levels")
    SystemFileSystem.createDirectories(directoryPath)
    SystemFileSystem.sink(Path("levels", "$name.level")).buffered().use { sink ->
        sink.write(bytes)
    }
}

fun Level.Companion.readFromFile(name: String): Level = runCatching {
    SystemFileSystem.source(Path("levels", "$name.level")).buffered().use { source ->
        val bytes = source.readByteArray()
        cbor.decodeFromByteArray<Level>(bytes)
    }
}.getOrElse { Level(name, emptyList()) }
