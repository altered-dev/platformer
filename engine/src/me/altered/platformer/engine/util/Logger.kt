package me.altered.platformer.engine.util

interface Logger {

    val level: Level

    fun v(tag: String, message: String)

    fun d(tag: String, message: String)

    fun i(tag: String, message: String)

    fun w(tag: String, message: String)

    fun e(tag: String, message: String)

    fun Logger.v(tag: String, message: Any?) = v(tag, message.toString())

    fun Logger.d(tag: String, message: Any?) = d(tag, message.toString())

    fun Logger.i(tag: String, message: Any?) = i(tag, message.toString())

    fun Logger.w(tag: String, message: Any?) = w(tag, message.toString())

    fun Logger.e(tag: String, message: Any?) = e(tag, message.toString())

    enum class Level { VERBOSE, DEBUG, INFO, WARNING, ERROR, NONE }

    companion object Default : Logger {

        var defaultLogger: Logger = PrintLogger(Level.VERBOSE)

        override val level: Level
            get() = defaultLogger.level

        override fun v(tag: String, message: String) = defaultLogger.v(tag, message)

        override fun d(tag: String, message: String) = defaultLogger.d(tag, message)

        override fun i(tag: String, message: String) = defaultLogger.i(tag, message)

        override fun w(tag: String, message: String) = defaultLogger.w(tag, message)

        override fun e(tag: String, message: String) = defaultLogger.e(tag, message)
    }
}
