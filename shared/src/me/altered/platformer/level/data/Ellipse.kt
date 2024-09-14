package me.altered.platformer.level.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.timeline.Expression

@Serializable
@SerialName("ellipse")
data class Ellipse(
    override val name: String,
    override val x: Expression<Float>,
    override val y: Expression<Float>,
    override val rotation: Expression<Float>,
    override val width: Expression<Float>,
    override val height: Expression<Float>,
    override val fill: Expression<Brush>,
    override val stroke: Expression<Brush>,
    override val strokeWidth: Expression<Float>,
) : Object, Object.Filled, Object.Stroked
