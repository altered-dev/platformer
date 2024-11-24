@file:OptIn(ExperimentalSerializationApi::class)

package me.altered.platformer.level

import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import me.altered.platformer.level.objects.Object

/**
 * An immutable representation of a fully loaded level.
 *
 * @see MutableLevel
 */
sealed interface Level {

    val name: String
    val objects: List<Object>

    fun DrawScope.draw()

    /**
     * Evaluates all objects in the level.
     *
     * NOTE: **DO NOT** optimize for time equality, as some expressions may rely not just on time.
     */
    fun eval(time: Float)

    fun toMutableLevel(): MutableLevel

    companion object
}

private val cbor = Cbor {
    encodeDefaults = true
    ignoreUnknownKeys = true
}

fun Level.saveToFile() {
    val level = when (this) {
        is LevelImpl -> this
        is MutableLevelImpl -> toLevel() as LevelImpl
    }
    val bytes = cbor.encodeToByteArray(level)
    SystemFileSystem.sink(Path("levels", "${level.name}.level")).buffered().use { sink ->
        sink.write(bytes)
    }
}

fun Level.Companion.readFromFile(name: String): Level = runCatching {
    SystemFileSystem.source(Path("levels", "$name.level")).buffered().use { source ->
        val bytes = source.readByteArray()
        cbor.decodeFromByteArray<LevelImpl>(bytes)
    }
}.getOrElse { LevelImpl(name, emptyList()) }
