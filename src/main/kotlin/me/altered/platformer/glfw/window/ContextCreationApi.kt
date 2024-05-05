package me.altered.platformer.glfw.window

import me.altered.platformer.glfw.IntEnum
import org.lwjgl.glfw.GLFW

enum class ContextCreationApi(override val code: Int) : IntEnum {
    NATIVE(GLFW.GLFW_NATIVE_CONTEXT_API),
    EGL(GLFW.GLFW_EGL_CONTEXT_API),
    OSMESA(GLFW.GLFW_OSMESA_CONTEXT_API),
}
