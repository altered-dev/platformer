package me.altered.platformer.objects

import me.altered.koml.Vector2fc
import me.altered.platformer.editor.Brush
import me.altered.platformer.editor.emptyBrush
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.const
import org.jetbrains.skia.Color4f

inline fun world(time: Float = 0.0f, builder: ObjectContainer.() -> Unit = {}): World {
    return World(time).apply(builder)
}

fun ObjectContainer.rectangle(
    name: String,
    x: Expression<Float>,
    y: Expression<Float>,
    rotation: Expression<Float>,
    width: Expression<Float>,
    height: Expression<Float>,
    fill: Expression<Brush> = const(emptyBrush()),
    stroke: Expression<Brush> = const(emptyBrush()),
    strokeWidth: Expression<Float> = const(0.0f),
) = Rectangle(
    name = name,
    xExpr = x,
    yExpr = y,
    rotationExpr = rotation,
    widthExpr = width,
    heightExpr = height,
    fillExpr = fill,
    strokeExpr = stroke,
    strokeWidthExpr = strokeWidth,
).also(::place)

fun ObjectContainer.ellipse(
    name: String,
    x: Expression<Float>,
    y: Expression<Float>,
    rotation: Expression<Float>,
    width: Expression<Float>,
    height: Expression<Float>,
    fill: Expression<Brush> = const(emptyBrush()),
    stroke: Expression<Brush> = const(emptyBrush()),
    strokeWidth: Expression<Float> = const(0.0f),
) = Ellipse(
    name = name,
    xExpr = x,
    yExpr = y,
    rotationExpr = rotation,
    widthExpr = width,
    heightExpr = height,
    fillExpr = fill,
    strokeExpr = stroke,
    strokeWidthExpr = strokeWidth,
).also(::place)

fun ObjectContainer.polygon(
    name: String,
    x: Expression<Float>,
    y: Expression<Float>,
    rotation: Expression<Float>,
    vararg points: Expression<Vector2fc>,
    fill: Expression<Color4f> = const(Colors.Transparent),
    stroke: Expression<Color4f> = const(Colors.Transparent),
    strokeWidth: Expression<Float> = const(0.0f),
) = Polygon(
    name = name,
    xExpr = x,
    yExpr = y,
    rotationExpr = rotation,
    fillExpr = fill,
    strokeExpr = stroke,
    strokeWidthExpr = strokeWidth,
    points = points.asList(),
).also(::place)

fun ObjectContainer.group(
    name: String,
    x: Expression<Float>,
    y: Expression<Float>,
    rotation: Expression<Float>,
    children: ObjectContainer.() -> Unit = {},
) = Group(
    name = name,
    xExpr = x,
    yExpr = y,
    rotationExpr = rotation,
).apply(children).also(::place)
