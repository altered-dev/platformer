package me.altered.platformer.engine.logger

import android.util.Log

class AndroidLogger : AbstractLogger() {

    override val level: Logger.Level
        get() = Logger.Level.Verbose

    override fun log(level: Logger.Level, tag: String, message: String) {
        Log.println(level.toPriority(), tag, message)
    }

    private fun Logger.Level.toPriority(): Int = when (this) {
        Logger.Level.Verbose -> Log.VERBOSE
        Logger.Level.Debug -> Log.DEBUG
        Logger.Level.Info -> Log.INFO
        Logger.Level.Warning -> Log.WARN
        Logger.Level.Error -> Log.ERROR
        Logger.Level.None -> 0
    }
}
