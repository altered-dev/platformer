package me.altered.platformer.objects

import me.altered.platformer.timeline.Expression

interface PlayerContext {

    val x: Expression<Float>
    val y: Expression<Float>
}
