package me.altered.platformer.level.data

import me.altered.platformer.timeline.Expression

data class Group(
    override val name: String,
    override val x: Expression<Float>,
    override val y: Expression<Float>,
    override val rotation: Expression<Float>,
    override val width: Expression<Float>,
    override val height: Expression<Float>,
    val children: List<Object>,
) : Object
