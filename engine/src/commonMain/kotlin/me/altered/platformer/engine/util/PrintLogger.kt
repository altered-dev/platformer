package me.altered.platformer.engine.util

class PrintLogger(
    override val level: Logger.Level = Logger.Level.Info,
) : AbstractLogger() {

    override fun log(message: String) = println(message)
}
