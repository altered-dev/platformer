package me.altered.platformer.level.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import me.altered.platformer.engine.logger.Logger
import me.altered.platformer.engine.logger.d
import me.altered.platformer.level.data.Level
import me.altered.platformer.level.data.ensureImmutable

class LevelRepositoryImpl(
    private val cbor: Cbor = Cbor {
        encodeDefaults = true
        ignoreUnknownKeys = true
    },
    private val localLevelsDirectory: Path = Path("levels"),
) : LevelRepository {

    override suspend fun save(level: Level) = runCatching {
        withContext(Dispatchers.IO) {
            Logger.d(TAG) { "saving level ${level.name}" }
            val level = level.ensureImmutable()
            val bytes = cbor.encodeToByteArray(level)
            SystemFileSystem.createDirectories(localLevelsDirectory)
            SystemFileSystem.sink(Path(localLevelsDirectory, path(level.name))).buffered().use { sink ->
                sink.write(bytes)
            }
        }
    }

    override suspend fun load(name: String) = runCatching {
        withContext(Dispatchers.IO) {
            Logger.d(TAG) { "loading level $name" }
            SystemFileSystem.source(Path(localLevelsDirectory, path(name))).buffered().use { source ->
                val bytes = source.readByteArray()
                cbor.decodeFromByteArray<Level>(bytes)
            }
        }
    }

    private fun path(name: String) = "$name.level"

    companion object {

        private const val TAG = "LevelRepository"
    }
}
