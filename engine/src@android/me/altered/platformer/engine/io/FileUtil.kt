package me.altered.platformer.engine.io

import org.jetbrains.skia.Font

actual fun Any.resource(path: String): String? {
    return null
//    val path = if (path.startsWith('/')) path else "/$path"
//    return javaClass.getResource(path)?.file
}

actual fun font(path: String?, size: Float): Font {
    TODO()
//    val path = path?.substringAfter('/')
//    return Font(null?.let { Typeface.makeFromFile(it) }, size)
}
