package me.altered.platformer.glfw.window

import me.altered.platformer.glfw.IntEnum
import org.lwjgl.glfw.GLFW

enum class ReleaseBehavior(override val code: Int) : IntEnum {
    ANY(GLFW.GLFW_ANY_RELEASE_BEHAVIOR),
    FLUSH(GLFW.GLFW_RELEASE_BEHAVIOR_FLUSH),
    NONE(GLFW.GLFW_RELEASE_BEHAVIOR_NONE),
}
