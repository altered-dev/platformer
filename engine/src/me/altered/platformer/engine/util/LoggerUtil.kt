package me.altered.platformer.engine.util

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
