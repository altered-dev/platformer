package me.altered.platformer.engine.util

fun Logger.v(tag: String, message: Any?) = v(tag, message.toString())

fun Logger.d(tag: String, message: Any?) = d(tag, message.toString())

fun Logger.i(tag: String, message: Any?) = i(tag, message.toString())

fun Logger.w(tag: String, message: Any?) = w(tag, message.toString())

fun Logger.e(tag: String, message: Any?) = e(tag, message.toString())

inline fun Logger.v(tag: String, lazyMessage: () -> Any?) {
    if (level >= Logger.Level.VERBOSE) {
        v(tag, lazyMessage())
    }
}

inline fun Logger.d(tag: String, lazyMessage: () -> Any?) {
    if (level >= Logger.Level.DEBUG) {
        d(tag, lazyMessage())
    }
}

inline fun Logger.i(tag: String, lazyMessage: () -> Any?) {
    if (level >= Logger.Level.INFO) {
        i(tag, lazyMessage())
    }
}

inline fun Logger.w(tag: String, lazyMessage: () -> Any?) {
    if (level >= Logger.Level.WARNING) {
        w(tag, lazyMessage())
    }
}

inline fun Logger.e(tag: String, lazyMessage: () -> Any?) {
    if (level >= Logger.Level.ERROR) {
        e(tag, lazyMessage())
    }
}
