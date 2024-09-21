package me.altered.platformer.level.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.expression.Expression

@Serializable
@SerialName("group")
data class Group(
    override val name: String,
    override val x: Expression<Float>,
    override val y: Expression<Float>,
    override val rotation: Expression<Float>,
    override val width: Expression<Float>,
    override val height: Expression<Float>,
    val children: List<Object>,
) : Object
