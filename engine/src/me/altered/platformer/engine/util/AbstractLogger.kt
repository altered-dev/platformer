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

    final override fun v(tag: String, message: String) {
        if (level <= Logger.Level.VERBOSE) {
            log("${padTag(tag)} v: $message")
        }
    }

    final override fun d(tag: String, message: String) {
        if (level <= Logger.Level.DEBUG) {
            log("${padTag(tag)} d: $message")
        }
    }

    final override fun i(tag: String, message: String) {
        if (level <= Logger.Level.INFO) {
            log("${padTag(tag)} i: $message")
        }
    }

    final override fun w(tag: String, message: String) {
        if (level <= Logger.Level.WARNING) {
            log("${padTag(tag)} w: $message")
        }
    }

    final override fun e(tag: String, message: String) {
        if (level <= Logger.Level.ERROR) {
            log("${padTag(tag)} e: $message")
        }
    }
}