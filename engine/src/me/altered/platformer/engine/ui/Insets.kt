package me.altered.platformer.engine.ui

data class Insets(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
) {

    val vertical: Float
        get() = top + bottom

    val horizontal: Float
        get() = left + right
}

val none: Insets
    get() = Insets(0.0f, 0.0f, 0.0f, 0.0f)

fun all(size: Float) = Insets(size, size, size, size)

fun vertical(size: Float) = Insets(0.0f, size, 0.0f, size)

fun horizontal(size: Float) = Insets(size, 0.0f, size, 0.0f)

fun each(
    left: Float = 0.0f,
    top: Float = 0.0f,
    right: Float = 0.0f,
    bottom: Float = 0.0f,
) = Insets(left, top, right, bottom)
