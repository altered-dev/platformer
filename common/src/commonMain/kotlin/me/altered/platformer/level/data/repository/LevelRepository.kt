package me.altered.platformer.level.data.repository

import me.altered.platformer.level.data.Level

interface LevelRepository {

    suspend fun save(level: Level): Result<Unit>

    suspend fun load(name: String): Result<Level>
}
