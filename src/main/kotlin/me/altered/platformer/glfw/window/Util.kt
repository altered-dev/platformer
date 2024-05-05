package me.altered.platformer.glfw.window

import org.lwjgl.glfw.GLFW
import org.lwjgl.system.MemoryUtil
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun createWindow(
    width: Int,
    height: Int,
    title: String,
    monitor: Long? = null,
    share: Long? = null,
    hints: WindowHints.() -> Unit = {},
): Window? {
    contract { callsInPlace(hints, InvocationKind.EXACTLY_ONCE) }
    GLFW.glfwDefaultWindowHints()
    WindowHints.hints()
    val pointer = GLFW.glfwCreateWindow(width, height, title, monitor ?: MemoryUtil.NULL, share ?: MemoryUtil.NULL)
    if (pointer == MemoryUtil.NULL) return null
    return Window(pointer)
}
