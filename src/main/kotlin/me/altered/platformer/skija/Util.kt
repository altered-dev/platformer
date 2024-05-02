package me.altered.platformer.skija

import io.github.humbleui.skija.Canvas
import io.github.humbleui.skija.Color4f
import io.github.humbleui.skija.Paint
import io.github.humbleui.skija.Path
import org.joml.Vector2f

fun Canvas.clear(color4f: Color4f) = clear(color4f.toColor())

fun Canvas.translate(vec: Vector2f) = translate(vec.x, vec.y)

inline fun buildPaint(block: Paint.() -> Unit): Paint = Paint().apply(block)

inline fun buildPath(block: Path.() -> Unit): Path = Path().apply(block)
