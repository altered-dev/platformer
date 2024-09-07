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

fun padding() = Insets(0.0f, 0.0f, 0.0f, 0.0f)

fun padding(all: Float) = Insets(all, all, all, all)

fun padding(
    horizontal: Float = 0.0f,
    vertical: Float = 0.0f,
) = Insets(horizontal, vertical, horizontal, vertical)

fun padding(
    left: Float = 0.0f,
    top: Float = 0.0f,
    right: Float = 0.0f,
    bottom: Float = 0.0f,
) = Insets(left, top, right, bottom)
