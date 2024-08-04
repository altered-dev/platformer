package me.altered.platformer.timeline

inline fun <T> named(name: String, crossinline block: (time: Float) -> T): Expression<T> = object : Expression<T> {
    override fun eval(time: Float): T = block(time)
    override fun toString(): String = name
}
