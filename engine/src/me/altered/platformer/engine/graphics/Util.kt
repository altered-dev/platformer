@file:Suppress("FunctionName")

package me.altered.platformer.engine.graphics

import me.altered.koml.Vector2fc
import me.altered.platformer.engine.node2d.Node2D
import me.altered.platformer.engine.ui.Insets
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.RRect
import org.jetbrains.skia.Rect
import org.jetbrains.skia.TextBlob

fun emptyRect(): Rect = Rect(0.0f, 0.0f, 0.0f, 0.0f)

fun Canvas.clear(color: Color) = clear(color.value)

fun Canvas.translate(vec: Vector2fc) = translate(vec.x, vec.y)

fun Canvas.scale(vec: Vector2fc) = scale(vec.x, vec.y)

fun Canvas.skew(vec: Vector2fc) = skew(vec.x, vec.y)

fun Canvas.scale(scalar: Float) = scale(scalar, scalar)

// TODO: to canvas delegate
fun Canvas.drawRect(rect: Rect, paint: Paint) = drawRect(rect, paint.nativePaint)

fun Canvas.drawRRect(rRect: RRect, paint: Paint) = drawRRect(rRect, paint.nativePaint)

fun Canvas.drawOval(rect: Rect, paint: Paint) = drawOval(rect, paint.nativePaint)

fun Canvas.drawLine(x0: Float, y0: Float, x1: Float, y1: Float, paint: Paint) = drawLine(x0, y0, x1, y1, paint.nativePaint)

fun Canvas.drawTextBlob(blob: TextBlob, x: Float, y: Float, paint: Paint) = drawTextBlob(blob, x, y, paint.nativePaint)

fun Canvas.drawCircle(x: Float, y: Float, radius: Float, paint: Paint) = drawCircle(x, y, radius, paint.nativePaint)

// TODO: transform matrix
fun Canvas.transform(node: Node2D) = this
    .translate(node.position)
    .rotate(node.rotation)
    .scale(node.scale)
    .skew(node.skew)

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