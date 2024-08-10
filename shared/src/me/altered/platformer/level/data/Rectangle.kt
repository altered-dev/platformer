package me.altered.platformer.level.data

import me.altered.platformer.editor.Brush
import me.altered.platformer.timeline.Expression

data class Rectangle(
    override val name: String,
    override val x: Expression<Float>,
    override val y: Expression<Float>,
    override val rotation: Expression<Float>,
    override val width: Expression<Float>,
    override val height: Expression<Float>,
    val cornerRadius: Expression<Float>,
    override val fill: Expression<Brush>,
    override val stroke: Expression<Brush>,
    override val strokeWidth: Expression<Float>,
) : Object, Object.Filled, Object.Stroked
