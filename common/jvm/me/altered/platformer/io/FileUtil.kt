package me.altered.platformer.io

import org.jetbrains.skia.Font
import org.jetbrains.skia.Typeface
import me.altered.platformer.Main
import org.jetbrains.skia.makeFromFile
import java.net.URL

fun resource(path: String): URL? {
//    val path = if (path.startsWith("/")) path else "/$path"
    return Main.javaClass.getResource(path)
}

fun font(path: String): Font {
    return Font(resource(path)?.file?.dropWhile { it == '/' }?.let { Typeface.makeFromFile(it) })
}

// TODO: auto-generate resource objects
fun font(path: String, size: Float): Font {
    return Font(resource(path)?.file?.dropWhile { it == '/' }?.let { Typeface.makeFromFile(it) }, size)
}
