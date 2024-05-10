package me.altered.platformer.glfw.window

import me.altered.platformer.glfw.IntEnum
import org.lwjgl.glfw.GLFW

enum class OpenGLProfile(override val code: Int) : IntEnum {
    ANY_PROFILE(GLFW.GLFW_OPENGL_ANY_PROFILE),
    COMPAT_PROFILE(GLFW.GLFW_OPENGL_COMPAT_PROFILE),
    CORE_PROFILE(GLFW.GLFW_OPENGL_CORE_PROFILE),
}
