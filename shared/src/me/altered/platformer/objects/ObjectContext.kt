package me.altered.platformer.objects

import me.altered.platformer.timeline.Expression

interface ObjectContext {

    val x: Expression<Float>
    val y: Expression<Float>
    val rotation: Expression<Float>
}
