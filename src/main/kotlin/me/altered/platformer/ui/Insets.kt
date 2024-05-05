package me.altered.platformer.ui

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

fun all(size: Size.Fixed) = Insets(size.value, size.value, size.value, size.value)

fun vertical(size: Size.Fixed) = Insets(0.0f, size.value, 0.0f, size.value)

fun horizontal(size: Size.Fixed) = Insets(size.value, 0.0f, size.value, 0.0f)

fun each(
    left: Float = 0.0f,
    top: Float = 0.0f,
    right: Float = 0.0f,
    bottom: Float = 0.0f,
) = Insets(left, top, right, bottom)
