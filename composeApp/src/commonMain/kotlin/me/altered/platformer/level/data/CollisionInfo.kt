package me.altered.platformer.level.data

import androidx.compose.ui.geometry.Offset

data class CollisionInfo(
    val point: Offset,
    val normal: Offset,
)
