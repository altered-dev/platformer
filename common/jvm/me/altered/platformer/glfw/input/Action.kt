package me.altered.platformer.glfw.input

import me.altered.platformer.glfw.IntEnum
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.glfw.GLFW.GLFW_REPEAT

enum class Action(override val code: Int) : IntEnum {
    RELEASE(GLFW_RELEASE),
    PRESS(GLFW_PRESS),
    REPEAT(GLFW_REPEAT),
}
