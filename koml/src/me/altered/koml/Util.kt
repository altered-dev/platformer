package me.altered.koml

import kotlin.math.cos
import kotlin.math.sin

const val PI = kotlin.math.PI.toFloat()

fun Float.deg2rad(): Float {
    return this / 180.0f * PI
}

fun Float.rad2deg(): Float {
    return this / PI * 180.0f
}

fun Iterable<Vector2fc>.sum(): Vector2fc {
    var sumX = 0.0f
    var sumY = 0.0f
    for (element in this) {
        sumX += element.x
        sumY += element.y
    }
    return Vector2f(sumX, sumY)
}

fun Iterable<Vector2fc>.average(): Vector2fc {
    var sumX = 0.0f
    var sumY = 0.0f
    var count = 0
    for (element in this) {
        sumX += element.x
        sumY += element.y
        count++
    }
    return if (count == 0) {
        Vector2f(Float.NaN, Float.NaN)
    } else {
        Vector2f(sumX / count, sumY / count)
    }
}

fun lerp(from: Float, to: Float, t: Float): Float {
    return from + t * (to - from)
}

fun Vector2fc.rotateAround(point: Vector2fc, deg: Float): Vector2fc {
    val sin = sin(deg.deg2rad())
    val cos = cos(deg.deg2rad())

//    val dx =           ((x - point.x) * cos) - ((point.y - y) * sin) + point.x
//    val dy = point.y - ((point.y - y) * cos) + ((x - point.x) * sin)
//    val dy = ((x - point.x) * sin) + ((point.y - y) * cos) + point.y

    val dx = ((x - point.x) * cos) - ((point.y - y) * sin) + point.x
    val dy =  point.y - ((point.y - y) * cos) - ((x - point.x) * sin)

    return Vector2f(dx, dy)
}