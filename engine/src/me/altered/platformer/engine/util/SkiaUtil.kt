package me.altered.platformer.engine.util

import me.altered.koml.Vector2f
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color4f
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Path
import org.jetbrains.skia.Rect

fun color(hex: Number): Color4f = Color4f(hex.toInt())

fun color(r: Float, g: Float, b: Float, a: Float = 1.0f) = Color4f(r, g, b, a)

fun Canvas.clear(color4f: Color4f) = clear(color4f.toColor())

fun Canvas.translate(vec: Vector2f) = translate(vec.x, vec.y)

fun Canvas.scale(vec: Vector2f) = scale(vec.x, vec.y)

inline fun paint(block: Paint.() -> Unit): Paint = Paint().apply(block)

inline fun path(block: Path.() -> Unit): Path = Path().apply(block)

fun Rect.offset(vec: Vector2f) = offset(vec.x, vec.y)

fun Rect.contains(x: Float, y: Float): Boolean {
    return x in left..right && y in top..bottom
}
