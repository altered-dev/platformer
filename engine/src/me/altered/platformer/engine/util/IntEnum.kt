package me.altered.platformer.engine.util

interface IntEnum {
    val code: Int
}

inline fun <reified T> enumValueOf(code: Int): T where T : Enum<T>, T : IntEnum {
    return enumValues<T>().first { it.code == code }
}
