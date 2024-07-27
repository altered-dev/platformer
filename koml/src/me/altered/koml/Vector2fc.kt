package me.altered.koml

interface Vector2fc {

    val x: Float
    val y: Float

    val length: Float
    val lengthSquared: Float

    val minComponent: Int
    val maxComponent: Int

    fun isFinite(): Boolean
    fun isNaN(): Boolean

    fun copy(dest: Vector2f = Vector2f()): Vector2f
    operator fun unaryPlus(): Vector2fc = copy()
    fun negate(dest: Vector2f = Vector2f()): Vector2f
    operator fun unaryMinus(): Vector2fc = negate()

    fun normalize(length: Float = 1.0f, dest: Vector2f = Vector2f()): Vector2fc
    fun perpendicular(dest: Vector2f = Vector2f()): Vector2fc

    @Throws(IllegalArgumentException::class)
    operator fun get(component: Int): Float

    operator fun plus(other: Vector2fc): Vector2fc = add(other)
    operator fun minus(other: Vector2fc): Vector2fc = sub(other)
    operator fun times(other: Vector2fc): Vector2fc = mul(other)
    operator fun div(other: Vector2fc): Vector2fc = frc(other)

    fun add(other: Vector2fc, dest: Vector2f = Vector2f()): Vector2f
    fun sub(other: Vector2fc, dest: Vector2f = Vector2f()): Vector2f
    fun mul(other: Vector2fc, dest: Vector2f = Vector2f()): Vector2f
    fun frc(other: Vector2fc, dest: Vector2f = Vector2f()): Vector2f

    operator fun times(scalar: Float): Vector2fc = mul(scalar)
    operator fun div(scalar: Float): Vector2fc = frc(scalar)

    fun mul(scalar: Float, dest: Vector2f = Vector2f()): Vector2f
    fun frc(scalar: Float, dest: Vector2f = Vector2f()): Vector2f

    infix fun dot(other: Vector2fc): Float
    infix fun angle(other: Vector2fc): Float
    infix fun distance(other: Vector2fc): Float
    infix fun distanceSquared(other: Vector2fc): Float

    operator fun times(mat: Matrix2fc): Vector2fc = mul(mat)

    fun mul(mat: Matrix2fc, dest: Vector2f = Vector2f()): Vector2f
    fun mulTranspose(mat: Matrix2fc, dest: Vector2f = Vector2f()): Vector2f

    fun lerp(other: Vector2fc, t: Float, dest: Vector2f = Vector2f()): Vector2f
    fun fma(a: Vector2fc, b: Vector2fc, dest: Vector2f = Vector2f()): Vector2f
    fun fma(a: Float, b: Vector2fc, dest: Vector2f = Vector2f()): Vector2f

    fun min(other: Vector2fc, dest: Vector2f = Vector2f()): Vector2f
    fun max(other: Vector2fc, dest: Vector2f = Vector2f()): Vector2f

    fun floor(dest: Vector2f = Vector2f()): Vector2f
    fun ceil(dest: Vector2f = Vector2f()): Vector2f
    fun round(dest: Vector2f = Vector2f()): Vector2f
    fun abs(dest: Vector2f = Vector2f()): Vector2f

    fun equals(other: Vector2fc, delta: Float): Boolean
    fun equals(x: Float, y: Float): Boolean

    operator fun component1(): Float
    operator fun component2(): Float
}
