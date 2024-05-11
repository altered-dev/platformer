package me.altered.platformer.engine.io

import org.jetbrains.skia.Font

expect fun Any.resource(path: String): String?

expect fun font(path: String?, size: Float): Font
