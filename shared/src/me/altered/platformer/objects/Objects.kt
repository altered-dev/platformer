package me.altered.platformer.objects

import me.altered.platformer.engine.util.Colors
import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.const
import org.jetbrains.skia.Color4f

fun rectangle(
    x: Expression<Float>,
    y: Expression<Float>,
    rotation: Expression<Float>,
    width: Expression<Float>,
    height: Expression<Float>,
    fill: Expression<Color4f> = const(Colors.Transparent),
    stroke: Expression<Color4f> = const(Colors.Transparent),
    strokeWidth: Expression<Float> = const(0.0f),
): Rectangle = Rectangle(
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
    x: Expression<Float>,
    y: Expression<Float>,
    rotation: Expression<Float>,
    width: Expression<Float>,
    height: Expression<Float>,
    fill: Expression<Color4f> = const(Colors.Transparent),
    stroke: Expression<Color4f> = const(Colors.Transparent),
    strokeWidth: Expression<Float> = const(0.0f),
): Ellipse = Ellipse(
    xExpr = x,
    yExpr = y,
    rotationExpr = rotation,
    widthExpr = width,
    heightExpr = height,
    fillExpr = fill,
    strokeExpr = stroke,
    strokeWidthExpr = strokeWidth,
)

fun group(
    x: Expression<Float>,
    y: Expression<Float>,
    rotation: Expression<Float>,
    vararg children: ObjectNode,
): Group = Group(
    xExpr = x,
    yExpr = y,
    rotationExpr = rotation,
).apply {
    addChildren(children.asIterable())
}
