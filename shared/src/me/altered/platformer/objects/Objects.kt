package me.altered.platformer.objects

import me.altered.koml.Vector2fc
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.const
import org.jetbrains.skia.Color4f

fun rectangle(
    name: String,
    x: Expression<Float>,
    y: Expression<Float>,
    rotation: Expression<Float>,
    width: Expression<Float>,
    height: Expression<Float>,
    fill: Expression<Color4f> = const(Colors.Transparent),
    stroke: Expression<Color4f> = const(Colors.Transparent),
    strokeWidth: Expression<Float> = const(0.0f),
): Rectangle = Rectangle(
    name = name,
    xExpr = x,
    yExpr = y,
    rotationExpr = rotation,
    widthExpr = width,
    heightExpr = height,
    fillExpr = fill,
    strokeExpr = stroke,
    strokeWidthExpr = strokeWidth,
)

fun ellipse(
    name: String,
    x: Expression<Float>,
    y: Expression<Float>,
    rotation: Expression<Float>,
    width: Expression<Float>,
    height: Expression<Float>,
    fill: Expression<Color4f> = const(Colors.Transparent),
    stroke: Expression<Color4f> = const(Colors.Transparent),
    strokeWidth: Expression<Float> = const(0.0f),
): Ellipse = Ellipse(
    name = name,
    xExpr = x,
    yExpr = y,
    rotationExpr = rotation,
    widthExpr = width,
    heightExpr = height,
    fillExpr = fill,
    strokeExpr = stroke,
    strokeWidthExpr = strokeWidth,
)

fun polygon(
    name: String,
    x: Expression<Float>,
    y: Expression<Float>,
    rotation: Expression<Float>,
    vararg points: Expression<Vector2fc>,
    fill: Expression<Color4f> = const(Colors.Transparent),
    stroke: Expression<Color4f> = const(Colors.Transparent),
    strokeWidth: Expression<Float> = const(0.0f),
): Polygon = Polygon(
    name = name,
    xExpr = x,
    yExpr = y,
    rotationExpr = rotation,
    fillExpr = fill,
    strokeExpr = stroke,
    strokeWidthExpr = strokeWidth,
    points = points.asList(),
)

fun group(
    name: String,
    x: Expression<Float>,
    y: Expression<Float>,
    rotation: Expression<Float>,
    vararg children: ObjectNode,
): Group = Group(
    name = name,
    xExpr = x,
    yExpr = y,
    rotationExpr = rotation,
).apply {
    addChildren(children.asIterable())
}
