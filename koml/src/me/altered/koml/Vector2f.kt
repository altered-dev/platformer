package me.altered.koml

import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round
import kotlin.math.sqrt

open class Vector2f(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
) : Vector2fc {

    override val length: Float
        get() = sqrt(x * x + y * y)
    override val lengthSquared: Float
        get() = x * x + y * y
    override val minComponent: Int
        get() = if (x >= y) 0 else 1
    override val maxComponent: Int
        get() = if (x >= y) 1 else 0

    override fun isFinite(): Boolean = x.isFinite() && y.isFinite()
    override fun isNaN(): Boolean = x.isNaN() || y.isNaN()

    override fun copy(dest: Vector2f): Vector2f {
        dest.x = x
        dest.y = y
        return dest
    }

    override fun negate(dest: Vector2f): Vector2f {
        dest.x = -x
        dest.y = -y
        return dest
    }

    fun negate() = negate(this)

    override fun normalize(length: Float, dest: Vector2f): Vector2f {
        val invLength = length / sqrt(x * x + y * y)
        dest.x = x * invLength
        dest.y = y * invLength
        return dest
    }

    fun normalize(length: Float): Vector2f = normalize(length, this)

    override fun perpendicular(dest: Vector2f): Vector2f {
        val xTemp = y
        dest.y = x * -1
        dest.x = xTemp
        return dest
    }

    fun perpendicular(): Vector2f = perpendicular(this)

    override fun get(component: Int): Float = when (component) {
        0 -> x
        1 -> y
        else -> throw IllegalArgumentException()
    }

    operator fun set(component: Int, value: Float) = when (component) {
        0 -> x = value
        1 -> y = value
        else -> throw IllegalArgumentException()
    }

    fun set(vec: Vector2fc, dest: Vector2f = this): Vector2f {
        dest.x = vec.x
        dest.y = vec.y
        return dest
    }

    fun set(
        x: Float,
        y: Float = x,
        dest: Vector2f = this,
    ): Vector2f {
        dest.x = x
        dest.y = y
        return dest
    }

    fun zero(): Vector2f {
        x = 0.0f
        y = 0.0f
        return this
    }

    fun add(other: Vector2fc) = add(other, this)
    operator fun plusAssign(other: Vector2fc) { add(other, this) }

    override fun add(other: Vector2fc, dest: Vector2f): Vector2f {
        dest.x = x + other.x
        dest.y = y + other.y
        return dest
    }

    fun sub(other: Vector2fc): Vector2f = sub(other, this)
    operator fun minusAssign(other: Vector2fc) { sub(other, this) }

    override fun sub(other: Vector2fc, dest: Vector2f): Vector2f {
        dest.x = x - other.x
        dest.y = y - other.y
        return dest
    }

    fun mul(other: Vector2fc): Vector2f = mul(other, this)
    operator fun timesAssign(other: Vector2fc) { mul(other, this) }

    override fun mul(other: Vector2fc, dest: Vector2f): Vector2f {
        dest.x = x * other.x
        dest.y = y * other.y
        return dest
    }

    fun mul(scalar: Float): Vector2f = mul(scalar, this)
    operator fun timesAssign(scalar: Float) { mul(scalar, this) }

    override fun mul(scalar: Float, dest: Vector2f): Vector2f {
        dest.x = x * scalar
        dest.y = y * scalar
        return dest
    }

    fun mul(mat: Matrix2fc): Vector2f = mul(mat, this)
    operator fun timesAssign(mat: Matrix2fc) { mul(mat, this) }

    override fun mul(mat: Matrix2fc, dest: Vector2f): Vector2f {
        val rx = mat.m00 * x + mat.m10 * y
        val ry = mat.m01 * x + mat.m11 * y
        dest.x = rx
        dest.y = ry
        return dest
    }

    fun frc(other: Vector2fc): Vector2f = frc(other, this)
    operator fun divAssign(other: Vector2fc) { frc(other, this) }

    override fun frc(other: Vector2fc, dest: Vector2f): Vector2f {
        dest.x = x / other.x
        dest.y = y / other.y
        return dest
    }

    fun frc(scalar: Float): Vector2f = frc(scalar, this)
    operator fun divAssign(scalar: Float) { frc(scalar, this) }

    override fun frc(scalar: Float, dest: Vector2f): Vector2f {
        dest.x = x / scalar
        dest.y = y / scalar
        return dest
    }

    override fun dot(other: Vector2fc): Float {
        return x * other.x + y * other.y
    }

    override fun angle(other: Vector2fc): Float {
        val dot = x * other.x + y * other.y
        val det = x * other.y - y * other.x
        return atan2(det, dot)
    }

    override fun distance(other: Vector2fc): Float {
        val dx = x - other.x
        val dy = y - other.y
        return sqrt(dx * dx + dy * dy)
    }

    override fun distanceSquared(other: Vector2fc): Float {
        val dx = x - other.x
        val dy = y - other.y
        return dx * dx + dy * dy
    }

    fun mulTranspose(mat: Matrix2fc): Vector2f = mulTranspose(mat, this)

    override fun mulTranspose(mat: Matrix2fc, dest: Vector2f): Vector2f {
        val rx = mat.m00 * x + mat.m01 * y
        val ry = mat.m10 * x + mat.m11 * y
        dest.x = rx
        dest.y = ry
        return dest
    }

    fun lerp(other: Vector2fc, t: Float): Vector2f = lerp(other, t, this)

    override fun lerp(other: Vector2fc, t: Float, dest: Vector2f): Vector2f {
        dest.x = x + (other.x - x) * t
        dest.y = y + (other.y - y) * t
        return dest
    }

    fun fma(a: Float, b: Vector2fc) = fma(a, b, this)

    override fun fma(a: Float, b: Vector2fc, dest: Vector2f): Vector2f {
        TODO("Not yet implemented")
    }

    fun fma(a: Vector2fc, b: Vector2fc) = fma(a, b, this)

    override fun fma(a: Vector2fc, b: Vector2fc, dest: Vector2f): Vector2f {
        TODO("Not yet implemented")
    }

    fun min(other: Vector2fc): Vector2f = min(other, this)

    override fun min(other: Vector2fc, dest: Vector2f): Vector2f {
        dest.x = min(x, other.x)
        dest.y = min(y, other.y)
        return dest
    }

    fun max(other: Vector2fc) = max(other, this)

    override fun max(other: Vector2fc, dest: Vector2f): Vector2f {
        dest.x = max(x, other.x)
        dest.y = max(y, other.y)
        return dest
    }

    fun floor() = floor(this)

    override fun floor(dest: Vector2f): Vector2f {
        dest.x = floor(x)
        dest.y = floor(y)
        return dest
    }

    fun ceil() = ceil(this)

    override fun ceil(dest: Vector2f): Vector2f {
        dest.x = ceil(x)
        dest.y = ceil(y)
        return dest
    }

    fun round() = round(this)

    override fun round(dest: Vector2f): Vector2f {
        dest.x = round(x)
        dest.y = round(y)
        return dest
    }

    fun abs() = abs(this)

    override fun abs(dest: Vector2f): Vector2f {
        dest.x = abs(x)
        dest.y = abs(y)
        return dest
    }

    override fun equals(other: Vector2fc, delta: Float): Boolean {
        return this == other ||
            abs(x - other.x) <= delta &&
            abs(y - other.y) <= delta
    }

    override fun equals(x: Float, y: Float): Boolean {
        return this.x == x && this.y == y
    }

    override fun component1(): Float = x
    override fun component2(): Float = y

    override fun toString(): String = "Vector2f(x=$x, y=$y)"

    companion object {

        fun length(x: Float, y: Float): Float {
            return sqrt(x * x + y * y)
        }

        fun lengthSquared(x: Float, y: Float): Float {
            return x * x + y * y
        }

        fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
            val dx = x1 - x2
            val dy = y1 - y2
            return sqrt(dx * dx + dy * dy)
        }

        fun distanceSquared(x1: Float, y1: Float, x2: Float, y2: Float): Float {
            val dx = x1 - x2
            val dy = y1 - y2
            return dx * dx + dy * dy
        }
    }
}
