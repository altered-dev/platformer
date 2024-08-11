package me.altered.platformer.level.data

import kotlinx.serialization.Serializable

@Serializable
data class Level(
    val name: String,
    val objects: List<Object>,
)
