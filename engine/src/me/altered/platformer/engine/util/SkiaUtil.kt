package me.altered.platformer.engine.util

import me.altered.koml.Transform2fc
import me.altered.koml.Vector2fc
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.ui.Insets
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color4f
import org.jetbrains.skia.Matrix33
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Path
import org.jetbrains.skia.Rect

fun color(hex: Number): Color4f = Color4f(hex.toInt())

fun color(r: Float, g: Float, b: Float, a: Float = 1.0f) = Color4f(r, g, b, a)

fun emptyRect(): Rect = Rect(0.0f, 0.0f, 0.0f, 0.0f)

fun Canvas.clear(color4f: Color4f) = clear(color4f.toColor())

fun Canvas.translate(vec: Vector2fc) = translate(vec.x, vec.y)

fun Canvas.scale(vec: Vector2fc) = scale(vec.x, vec.y)

fun Canvas.skew(vec: Vector2fc) = skew(vec.x, vec.y)

fun Canvas.scale(scalar: Float) = scale(scalar, scalar)

// TODO: transform matrix
fun Canvas.transform(node: Node2D) = this
    .translate(node.position)
    .rotate(node.rotation)
    .scale(node.scale)
    .skew(node.skew)

inline fun paint(block: Paint.() -> Unit): Paint = Paint().apply(block)

inline fun path(block: Path.() -> Unit): Path = Path().apply(block)

fun Rect.offset(vec: Vector2fc) = offset(vec.x, vec.y)

fun Rect.inset(insets: Insets) =
    Rect.makeLTRB(left + insets.left, top + insets.top, right - insets.right, bottom - insets.bottom)

fun Rect.outset(insets: Insets) =
    Rect.makeLTRB(left - insets.left, top - insets.top, right + insets.right, bottom + insets.bottom)

fun Rect.contains(x: Float, y: Float): Boolean {
    return x in left..right && y in top..bottom
}

operator fun Rect.contains(vec: Vector2fc): Boolean {
    return vec.x in left..right && vec.y in top..bottom
}

fun Transform2fc.toSkMatrix() = Matrix33(scaleX, skewX, transX, skewY, scaleY, transY, 0.0f, 0.0f, 1.0f)