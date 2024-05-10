package me.altered.platformer.io

import io.github.humbleui.skija.Font
import io.github.humbleui.skija.Typeface
import me.altered.platformer.Main
import java.net.URL

fun resource(path: String): URL? {
    val path = if (path.startsWith("/")) path else "/$path"
    return Main.javaClass.getResource(path)
}

fun font(path: String): Font {
    return Font(Typeface.makeFromFile(resource(path)?.file?.dropWhile { it == '/' }))
}

// TODO: auto-generate resource objects
fun font(path: String, size: Float): Font {
    return Font(Typeface.makeFromFile(resource(path)?.file?.dropWhile { it == '/' }), size)
}
