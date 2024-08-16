package me.altered.platformer.level.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.engine.graphics.Brush
import me.altered.platformer.timeline.Expression

@Serializable
@SerialName("polygon")
data class Polygon(
    override val name: String,
    override val x: Expression<Float>,
    override val y: Expression<Float>,
    override val rotation: Expression<Float>,
    // TODO: width and height should be dependent on the positions of the points
    // OR make them scale the point positions BUT actual bounds will need to be calculated
    override val width: Expression<Float>,
    override val height: Expression<Float>,
    override val fill: Expression<Brush>,
    override val stroke: Expression<Brush>,
    override val strokeWidth: Expression<Float>,
    val points: List<Expression<Float>>,
) : Object, Object.Filled, Object.Stroked
