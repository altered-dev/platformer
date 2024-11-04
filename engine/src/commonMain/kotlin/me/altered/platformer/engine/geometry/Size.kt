package me.altered.platformer.engine.geometry

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Size

@Stable
operator fun Size.times(other: Size): Size {
    return Size(width * other.width, height * other.height)
}

@Stable
operator fun Size.div(other: Size): Size {
    return Size(width / other.width, height / other.height)
}

@Stable
operator fun Float.div(size: Size): Size {
    return Size(this / size.width, this / size.height)
}
