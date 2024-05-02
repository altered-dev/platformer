package me.altered.platformer.glfw

import org.lwjgl.glfw.GLFW.glfwCreateWindow
import org.lwjgl.glfw.GLFW.glfwDefaultWindowHints
import org.lwjgl.glfw.GLFW.glfwInit
import org.lwjgl.glfw.GLFW.glfwTerminate
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.NULL
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun glfw(block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    try {
        check(glfwInit()) { "Failed to initialize GLFW." }
        block()
    } finally {
        glfwTerminate()
    }
}

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
    glfwDefaultWindowHints()
    WindowHints.hints()
    val pointer = glfwCreateWindow(width, height, title, monitor ?: NULL, share ?: NULL)
    if (pointer == NULL) return null
    return Window(pointer)
}

@OptIn(ExperimentalContracts::class)
inline fun <T> memoryStack(block: MemoryStack.() -> T): T {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return MemoryStack.stackPush().use(block)
}
