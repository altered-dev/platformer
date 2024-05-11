package me.altered.platformer.engine.io

import org.jetbrains.skia.Font
import org.jetbrains.skia.Typeface
import org.jetbrains.skia.makeFromFile

actual fun Any.resource(path: String): String? {
    val path = if (path.startsWith('/')) path else "/$path"
    return javaClass.getResource(path)?.file
}

actual fun font(path: String?, size: Float): Font {
    val path = path?.substringAfter('/')
    return Font(null?.let { Typeface.makeFromFile(it) }, size)
}
