package me.altered.koml

data class Matrix2f(
    override var m00: Float,
    override var m01: Float,
    override var m10: Float,
    override var m11: Float,
) : Matrix2fc
