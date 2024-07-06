package me.altered.platformer.engine.util

abstract class AbstractLogger : Logger {

    protected abstract fun log(message: String)

    private fun padTag(tag: String) = buildString {
        append('[')
        if (tag.length > 16) {
            append(tag, 0, 13)
            append("...")
        } else {
            append(tag)
        }
        append(']')
        repeat(18 - length) {
            append(' ')
        }
    }

    private fun padMessage(message: String) = message.replace("\n", "\n                      ")

    final override fun v(tag: String, message: String) {
        if (level <= Logger.Level.Verbose) {
            log("${padTag(tag)} v: ${padMessage(message)}")
        }
    }

    final override fun d(tag: String, message: String) {
        if (level <= Logger.Level.Debug) {
            log("${padTag(tag)} d: ${padMessage(message)}")
        }
    }

    final override fun i(tag: String, message: String) {
        if (level <= Logger.Level.Info) {
            log("${padTag(tag)} i: ${padMessage(message)}")
        }
    }

    final override fun w(tag: String, message: String) {
        if (level <= Logger.Level.Warning) {
            log("${padTag(tag)} w: ${padMessage(message)}")
        }
    }

    final override fun e(tag: String, message: String) {
        if (level <= Logger.Level.Error) {
            log("${padTag(tag)} e: ${padMessage(message)}")
        }
    }
}