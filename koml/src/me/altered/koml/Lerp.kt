package me.altered.koml

/**
 * Linearly interpolates [t] between [from] and [to].
 *
 * @param from the lower bound
 * @param to the upper bound
 * @param t the lerp value, usually between 0 and 1
 *
 * @return [from] if [t] is 0, [to] if [t] is 1, everything in between otherwise
 */
fun lerp(from: Float, to: Float, t: Float): Float {
    return from + t * (to - from)
}

/**
 * Linearly interpolates [t] between [from] and [to].
 *
 * @param from the lower bound
 * @param to the upper bound
 * @param t the lerp value, usually between 0 and 1
 *
 * @return [from] if [t] is 0, [to] if [t] is 1, everything in between otherwise
 */
fun lerp(from: Vector2fc, to: Vector2fc, t: Float, dest: Vector2f = Vector2f()): Vector2f {
    return from.lerp(to, t, dest)
}
