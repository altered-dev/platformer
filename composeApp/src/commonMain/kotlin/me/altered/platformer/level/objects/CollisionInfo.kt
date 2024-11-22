package me.altered.platformer.level.objects

import androidx.compose.ui.geometry.Offset

data class CollisionInfo(
    val point: Offset,
    val normal: Offset,
)
