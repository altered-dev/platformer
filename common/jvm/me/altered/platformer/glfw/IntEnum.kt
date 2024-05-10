package me.altered.platformer.glfw

import kotlin.enums.enumEntries

interface IntEnum {
    val code: Int
}

inline fun <reified T> enumValueOf(code: Int): T where T : Enum<T>, T : IntEnum {
    return enumEntries<T>().first { it.code == code }
}
