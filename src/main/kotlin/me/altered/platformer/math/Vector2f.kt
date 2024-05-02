package me.altered.platformer.math

import org.joml.Matrix2dc
import org.joml.Matrix2fc
import org.joml.Vector2f
import org.joml.Vector2fc

operator fun Vector2fc.unaryMinus(): Vector2f = negate(Vector2f())

operator fun Vector2fc.plus(vec: Vector2fc): Vector2f = add(vec, Vector2f())

operator fun Vector2f.plusAssign(vec: Vector2fc) { add(vec) }

operator fun Vector2fc.minus(vec: Vector2fc): Vector2f = sub(vec, Vector2f())

operator fun Vector2f.minusAssign(vec: Vector2fc) { sub(vec) }

operator fun Vector2fc.times(scalar: Float): Vector2f = mul(scalar, Vector2f())

operator fun Vector2f.timesAssign(scalar: Float) { mul(scalar) }

operator fun Float.times(vec: Vector2fc): Vector2f = vec.mul(this, Vector2f())

operator fun Vector2fc.div(scalar: Float): Vector2f = div(scalar, Vector2f())

operator fun Vector2f.divAssign(scalar: Float) { div(scalar) }

// TODO: divide number by vector



operator fun Vector2fc.times(mat: Matrix2fc): Vector2f = mul(mat, Vector2f())

operator fun Vector2f.timesAssign(mat: Matrix2fc) { mul(mat) }

operator fun Vector2fc.times(mat: Matrix2dc): Vector2f = mul(mat, Vector2f())

operator fun Vector2f.timesAssign(mat: Matrix2dc) { mul(mat) }



operator fun Vector2fc.component1() = x()

operator fun Vector2fc.component2() = y()
