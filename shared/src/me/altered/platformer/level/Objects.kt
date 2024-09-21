package me.altered.platformer.level

import me.altered.koml.Vector2f
import me.altered.platformer.level.data.Brush
import me.altered.platformer.level.data.emptyBrush
import me.altered.platformer.level.data.Ellipse
import me.altered.platformer.level.data.Group
import me.altered.platformer.level.data.Rectangle
import me.altered.platformer.level.node.EllipseNode
import me.altered.platformer.level.node.GroupNode
import me.altered.platformer.level.node.RectangleNode
import me.altered.platformer.expression.Expression
import me.altered.platformer.expression.const

/**
 * Creates a [World] object, adding objects specified in the builder.
 */
inline fun world(
    name: String = "World",
    scale: Float = 1.0f,
    position: Vector2f = Vector2f(),
    builder: WorldContext.() -> Unit,
): World {
    return World(name, null, scale, position).apply(builder)
}

fun ObjectContainer.rectangle(
    name: String = "rectangle",
    x: Expression<Float> = const(0.0f),
    y: Expression<Float> = const(0.0f),
    rotation: Expression<Float> = const(0.0f),
    width: Expression<Float> = const(1.0f),
    height: Expression<Float> = const(1.0f),
    cornerRadius: Expression<Float> = const(0.0f),
    fill: Expression<Brush> = const(emptyBrush()),
    stroke: Expression<Brush> = const(emptyBrush()),
    strokeWidth: Expression<Float> = const(0.0f),
) = place(RectangleNode(Rectangle(name, x, y, rotation, width, height, cornerRadius, fill, stroke, strokeWidth)))

fun ObjectContainer.ellipse(
    name: String = "ellipse",
    x: Expression<Float> = const(0.0f),
    y: Expression<Float> = const(0.0f),
    rotation: Expression<Float> = const(0.0f),
    width: Expression<Float> = const(1.0f),
    height: Expression<Float> = const(1.0f),
    fill: Expression<Brush> = const(emptyBrush()),
    stroke: Expression<Brush> = const(emptyBrush()),
    strokeWidth: Expression<Float> = const(0.0f),
) = place(EllipseNode(Ellipse(name, x, y, rotation, width, height, fill, stroke, strokeWidth)))

inline fun ObjectContainer.group(
    name: String = "group",
    x: Expression<Float> = const(0.0f),
    y: Expression<Float> = const(0.0f),
    rotation: Expression<Float> = const(0.0f),
    width: Expression<Float> = const(1.0f),
    height: Expression<Float> = const(1.0f),
    content: ObjectContainer.() -> Unit = {},
) = place(
    // this is quite hacky, needs rethinking
    GroupNode().apply {
        content()
        obj = Group(name, x, y, rotation, width, height, objects.mapNotNull { it.obj }.toList())
    }
)
