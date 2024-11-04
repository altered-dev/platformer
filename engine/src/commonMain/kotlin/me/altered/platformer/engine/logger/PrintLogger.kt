package me.altered.platformer.engine.logger

class PrintLogger(
    override val level: Logger.Level = Logger.Level.Info,
) : AbstractLogger() {

    override fun log(level: Logger.Level, tag: String, message: String) {
        println("${padTag(tag)} ${level.name.first()}: ${padMessage(message)}")
    }

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
}
