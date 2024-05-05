package me.altered.platformer.glfw.window

import me.altered.platformer.glfw.IntEnum
import org.lwjgl.glfw.GLFW

enum class ContextRobustness(override val code: Int) : IntEnum {
    NO_ROBUSTNESS(GLFW.GLFW_NO_ROBUSTNESS),
    NO_RESET_NOTIFICATION(GLFW.GLFW_NO_RESET_NOTIFICATION),
    LOSE_CONTEXT_ON_RESET(GLFW.GLFW_LOSE_CONTEXT_ON_RESET),
}
