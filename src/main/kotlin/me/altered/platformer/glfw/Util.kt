package me.altered.platformer.glfw

import org.lwjgl.glfw.GLFW.glfwInit
import org.lwjgl.glfw.GLFW.glfwTerminate
import org.lwjgl.system.MemoryStack
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
inline fun <T> memoryStack(block: MemoryStack.() -> T): T {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return MemoryStack.stackPush().use(block)
}
