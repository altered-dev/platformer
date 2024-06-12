package me.altered.koml

interface Matrix3fc {

    val m00: Float
    val m01: Float
    val m02: Float
    val m10: Float
    val m11: Float
    val m12: Float
    val m20: Float
    val m21: Float
    val m22: Float

    val minComponent: Float
    val maxComponent: Float

    val determinant: Float

    fun isFinite(): Boolean

    fun copy(dest: Matrix3f = Matrix3f()): Matrix3f
    operator fun unaryPlus(): Matrix3fc = copy()
    fun negate(dest: Matrix3f = Matrix3f()): Matrix3f
    operator fun unaryMinus(): Matrix3fc = negate()

    @Throws(IllegalArgumentException::class)
    operator fun get(component: Int): Float

    fun invert(dest: Matrix3f = Matrix3f()): Matrix3f
    fun transpose(dest: Matrix3f = Matrix3f()): Matrix3f

    operator fun plus(other: Matrix3fc): Matrix3fc = add(other)
    operator fun minus(other: Matrix3fc): Matrix3fc = sub(other)
    operator fun times(other: Matrix3fc): Matrix3fc = mul(other)
    operator fun div(other: Matrix3fc): Matrix3fc = frc(other)

    fun add(other: Matrix3fc, dest: Matrix3f = Matrix3f()): Matrix3f
    fun sub(other: Matrix3fc, dest: Matrix3f = Matrix3f()): Matrix3f
    fun mul(other: Matrix3fc, dest: Matrix3f = Matrix3f()): Matrix3f
    fun frc(other: Matrix3fc, dest: Matrix3f = Matrix3f()): Matrix3f

    operator fun times(scalar: Float): Matrix3fc = mul(scalar)
    operator fun div(scalar: Float): Matrix3fc = frc(scalar)

    fun mul(scalar: Float, dest: Matrix3f = Matrix3f()): Matrix3f
    fun frc(scalar: Float, dest: Matrix3f = Matrix3f()): Matrix3f

    fun mulLocal(other: Matrix3fc, dest: Matrix3f = Matrix3f()): Matrix3f
    fun mulLocal(scalar: Float, dest: Matrix3f = Matrix3f()): Matrix3f
}
