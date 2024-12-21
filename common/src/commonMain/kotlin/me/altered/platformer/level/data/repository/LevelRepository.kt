package me.altered.platformer.level.data.repository

import me.altered.platformer.level.data.Level

interface LevelRepository {

    suspend fun list(): List<String>

    suspend fun create(name: String = "New Level"): String

    suspend fun save(level: Level): Result<Unit>

    suspend fun load(name: String): Result<Level>
}
