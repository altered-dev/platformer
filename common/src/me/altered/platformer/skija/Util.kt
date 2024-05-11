package me.altered.platformer.skija

import me.altered.koml.Vector2f
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skia.Color4f
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Path
import org.jetbrains.skia.Rect

operator fun Color.invoke(hex: Long): Int = hex.toInt()

fun Canvas.clear(color4f: Color4f) = clear(color4f.toColor())

fun Canvas.translate(vec: Vector2f) = translate(vec.x, vec.y)

inline fun buildPaint(block: Paint.() -> Unit): Paint = Paint().apply(block)

inline fun buildPath(block: Path.() -> Unit): Path = Path().apply(block)

fun Rect.offset(vec: Vector2f) = offset(vec.x, vec.y)

fun Rect.contains(x: Float, y: Float): Boolean {
    return x in left..right && y in top..bottom
}
