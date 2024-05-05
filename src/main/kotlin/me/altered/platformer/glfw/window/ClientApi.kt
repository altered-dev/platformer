package me.altered.platformer.glfw.window

import me.altered.platformer.glfw.IntEnum
import org.lwjgl.glfw.GLFW

enum class ClientApi(override val code: Int) : IntEnum {
    OPENGL_API(GLFW.GLFW_OPENGL_API),
    OPENGL_ES_API(GLFW.GLFW_OPENGL_ES_API),
}
