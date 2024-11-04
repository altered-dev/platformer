package me.altered.platformer.engine.logger

fun Logger.v(tag: String, message: Any?) = v(tag, message.toString())

fun Logger.d(tag: String, message: Any?) = d(tag, message.toString())

fun Logger.i(tag: String, message: Any?) = i(tag, message.toString())

fun Logger.w(tag: String, message: Any?) = w(tag, message.toString())

fun Logger.e(tag: String, message: Any?) = e(tag, message.toString())

inline fun Logger.v(tag: String, lazyMessage: () -> Any?) {
    if (level >= Logger.Level.Verbose) {
        v(tag, lazyMessage())
    }
}

inline fun Logger.d(tag: String, lazyMessage: () -> Any?) {
    if (level >= Logger.Level.Debug) {
        d(tag, lazyMessage())
    }
}

inline fun Logger.i(tag: String, lazyMessage: () -> Any?) {
    if (level >= Logger.Level.Info) {
        i(tag, lazyMessage())
    }
}

inline fun Logger.w(tag: String, lazyMessage: () -> Any?) {
    if (level >= Logger.Level.Warning) {
        w(tag, lazyMessage())
    }
}

inline fun Logger.e(tag: String, lazyMessage: () -> Any?) {
    if (level >= Logger.Level.Error) {
        e(tag, lazyMessage())
    }
}

inline fun <T> T.andLog(tag: String): T = also { Logger.d(tag, this) }
