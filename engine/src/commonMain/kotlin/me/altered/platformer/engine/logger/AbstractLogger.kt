package me.altered.platformer.engine.logger

abstract class AbstractLogger : Logger {

    protected abstract fun log(level: Logger.Level, tag: String, message: String)

    final override fun v(tag: String, message: String) {
        if (level <= Logger.Level.Verbose) {
            log(Logger.Level.Verbose, tag, message)
        }
    }

    final override fun d(tag: String, message: String) {
        if (level <= Logger.Level.Debug) {
            log(Logger.Level.Debug, tag, message)
        }
    }

    final override fun i(tag: String, message: String) {
        if (level <= Logger.Level.Info) {
            log(Logger.Level.Info, tag, message)
        }
    }

    final override fun w(tag: String, message: String) {
        if (level <= Logger.Level.Warning) {
            log(Logger.Level.Warning, tag, message)
        }
    }

    final override fun e(tag: String, message: String) {
        if (level <= Logger.Level.Error) {
            log(Logger.Level.Error, tag, message)
        }
    }
}
