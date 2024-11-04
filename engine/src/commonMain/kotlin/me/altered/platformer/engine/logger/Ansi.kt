package me.altered.platformer.engine.logger

object Ansi {

    private const val pre = "\u001b["
    private const val post = "m"

    const val reset = pre + 0 + post
    const val bold = pre + 1 + post
    const val light = pre + 2 + post
    const val italic = pre + 3 + post
    const val underline = pre + 4 + post
    const val slowBlink = pre + 5 + post
    const val rapidBlink = pre + 6 + post
    const val invert = pre + 7 + post
    const val hide = pre + 8 + post
    const val strike = pre + 9 + post
    const val defaultFont = pre + 10 + post
    const val font1 = pre + 11 + post
    const val font2 = pre + 12 + post
    const val font3 = pre + 13 + post
    const val font4 = pre + 14 + post
    const val font5 = pre + 15 + post
    const val font6 = pre + 16 + post
    const val font7 = pre + 17 + post
    const val font8 = pre + 18 + post
    const val font9 = pre + 19 + post
    const val font10 = pre + 20 + post
    const val fraktur = pre + 21 + post
    const val doubleUnderline = pre + 22 + post
    const val normal = pre + 23 + post
    const val notItalic = pre + 24 + post
    const val notUnderlined = pre + 25 + post
    const val notBlinking = pre + 26 + post
    const val reveal = pre + 28 + post
    const val notStrike = pre + 29 + post


    class Builder {

        private val builder = StringBuilder()



        fun build() = builder.toString()
    }
}


inline fun buildAnsiString(block: Ansi.Builder.() -> Unit): String {
    return Ansi.Builder().apply(block).build()
}
