package me.altered.platformer.engine.util

// only open for Logger companion object
open class PrintLogger(
    final override val level: Logger.Level = Logger.Level.INFO,
) : AbstractLogger() {

    final override fun log(message: String) = println(message)
}
