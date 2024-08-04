package me.altered.platformer.timeline

import me.altered.koml.Vector2f
import me.altered.koml.Vector2fc

class VectorExpression(
    val x: Expression<Float>,
    val y: Expression<Float>,
) : Expression<Vector2fc> {

    override fun eval(time: Float): Vector2fc {
        return Vector2f(
            x = x.eval(time),
            y = y.eval(time),
        )
    }

    override fun toString() = "($x; $y)"
}

fun point(
    x: Expression<Float>,
    y: Expression<Float>,
): Expression<Vector2fc> = VectorExpression(x, y)

fun point(
    x: Float,
    y: Float,
): Expression<Vector2fc> = VectorExpression(const(x), const(y))

fun point(
    x: Expression<Float>,
    y: Float,
): Expression<Vector2fc> = VectorExpression(x, const(y))

fun point(
    x: Float,
    y: Expression<Float>,
): Expression<Vector2fc> = VectorExpression(const(x), y)
