package me.altered.platformer

fun Array<String>.getString(name: String, shortName: String? = null): String? {
    val arg = find { it.startsWith("--$name=") || (shortName != null && it.startsWith("-$shortName=")) }
    return arg?.substringAfter('=')
}

fun Array<String>.getString(name: String, shortName: String? = null, defaultValue: String): String {
    val arg = find { it.startsWith("--$name=") || (shortName != null && it.startsWith("-$shortName=")) }
    return arg?.substringAfter('=') ?: defaultValue
}

fun Array<String>.getInt(name: String, shortName: String? = null): Int? {
    val arg = find { it.startsWith("--$name=") || (shortName != null && it.startsWith("-$shortName=")) }
    return arg?.substringAfter('=')?.toIntOrNull()
}

fun Array<String>.getInt(name: String, shortName: String? = null, defaultValue: Int): Int {
    val arg = find { it.startsWith("--$name=") || (shortName != null && it.startsWith("-$shortName=")) }
    return arg?.substringAfter('=')?.toIntOrNull() ?: defaultValue
}

fun Array<String>.getBoolean(name: String, shortName: String? = null): Boolean {
    return any { it.startsWith("--$name") || (shortName != null && it.startsWith("-$shortName")) }
}
