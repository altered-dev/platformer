package me.altered.platformer.skija

import io.github.humbleui.skija.Canvas
import io.github.humbleui.skija.Color4f
import io.github.humbleui.skija.Paint
import io.github.humbleui.skija.Path
import io.github.humbleui.types.Rect
import org.joml.Vector2f

fun color(hex: Long) = Color4f(hex.toInt())

fun Canvas.clear(color4f: Color4f) = clear(color4f.toColor())

fun Canvas.translate(vec: Vector2f) = translate(vec.x, vec.y)

inline fun buildPaint(block: Paint.() -> Unit): Paint = Paint().apply(block)

inline fun buildPath(block: Path.() -> Unit): Path = Path().apply(block)

fun Rect.offset(vec: Vector2f) = offset(vec.x, vec.y)

operator fun Rect.contains(vec: Vector2f): Boolean {
    return vec.x in left..right && vec.y in top..bottom
}
