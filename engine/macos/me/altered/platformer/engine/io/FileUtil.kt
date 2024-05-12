package me.altered.platformer.engine.io

import org.jetbrains.skia.Font
import org.jetbrains.skia.Typeface
import org.jetbrains.skia.makeFromFile

actual fun Any.resource(path: String): String? {
    return null
}

actual fun font(path: String?, size: Float): Font {
    return Font(path?.let { Typeface.makeFromFile(it) }, size)
}
