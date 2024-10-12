package me.altered.koml

import kotlin.math.sqrt

/**
 * A representation of a two-dimensional (planar) vector in Euclidean space.
 */
interface Vector2fc {

    /**
     * The horizontal component of the vector.
     */
    val x: Float

    /**
     * The vertical component of the vector.
     */
    val y: Float

    /**
     * The total length of the vector.
     *
     * If the squared length is required, it is advised to use [lengthSquared], as it is more efficient.
     */
    val length: Float

    /**
     * The total length of the vector squared.
     *
     * It is usually more efficient than [length], since it doesn't contain [sqrt] in it.
     */
    val lengthSquared: Float

    /**
     * Returns the minimum of the two components ([x] and [y]).
     */
    val minComponent: Int

    /**
     * Returns the maximum of the two components ([x] and [y]).
     */
    val maxComponent: Int

    /**
     * Whether this vector only contains finite number components.
     */
    fun isFinite(): Boolean

    /**
     * Whether this vector contains a [Float.NaN] component.
     */
    fun isNaN(): Boolean

    /**
     * Returns a new vector that is equal to this vector.
     *
     * @param dest The vector that the changes will be applied to.
     */
    fun copy(dest: Vector2f = Vector2f()): Vector2f

    /**
     * Returns a new vector that is equal to this vector.
     */
    operator fun unaryPlus(): Vector2fc = copy()

    /**
     * Returns a new vector that has both components with inverted signs.
     *
     * @param dest The vector that the changes will be applied to.
     */
    fun negate(dest: Vector2f = Vector2f()): Vector2f

    /**
     * Returns a new vector that has both components with inverted signs.
     */
    operator fun unaryMinus(): Vector2fc = negate()

    /**
     * Returns a new vector that has the same direction (normal) as this vector, but with its length equal to [length].
     *
     * @param dest The vector that the changes will be applied to.
     */
    fun normalize(length: Float = 1.0f, dest: Vector2f = Vector2f()): Vector2fc

    /**
     * Returns a new vector that has the same length but is facing a perpendicular direction.
     *
     * @param dest The vector that the changes will be applied to.
     */
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

    fun add(x: Float = 0.0f, y: Float = 0.0f, dest: Vector2f = Vector2f()): Vector2f
    fun sub(x: Float = 0.0f, y: Float = 0.0f, dest: Vector2f = Vector2f()): Vector2f
    fun mul(x: Float = 0.0f, y: Float = 0.0f, dest: Vector2f = Vector2f()): Vector2f
    fun frc(x: Float = 0.0f, y: Float = 0.0f, dest: Vector2f = Vector2f()): Vector2f

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
