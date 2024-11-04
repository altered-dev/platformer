package me.altered.platformer.engine.logger

interface Logger {

    val level: Level

    fun v(tag: String, message: String)

    fun d(tag: String, message: String)

    fun i(tag: String, message: String)

    fun w(tag: String, message: String)

    fun e(tag: String, message: String)

    enum class Level { Verbose, Debug, Info, Warning, Error, None }

    companion object Default : Logger {

        var defaultLogger: Logger = PrintLogger(Level.Info)

        override val level: Level
            get() = defaultLogger.level

        override fun v(tag: String, message: String) = defaultLogger.v(tag, message)

        override fun d(tag: String, message: String) = defaultLogger.d(tag, message)

        override fun i(tag: String, message: String) = defaultLogger.i(tag, message)

        override fun w(tag: String, message: String) = defaultLogger.w(tag, message)

        override fun e(tag: String, message: String) = defaultLogger.e(tag, message)
    }
}
