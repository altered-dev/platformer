package me.altered.platformer.ui

sealed interface Size {

    @JvmInline
    value class Fixed(val value: Float) : Size

    data object Wrap : Size

    data object Expand : Size
}

val Float.px: Size.Fixed
    get() = Size.Fixed(this)

val Int.px: Size.Fixed
    get() = Size.Fixed(toFloat())

val wrap: Size
    get() = Size.Wrap

val expand: Size
    get() = Size.Expand
