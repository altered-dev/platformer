package me.altered.platformer.timeline

/**
 * A computable value to be used as a property of an object in a level.
 * TODO: serialize
 */
fun interface Expression<out T> {

    fun eval(time: Float): T
}
