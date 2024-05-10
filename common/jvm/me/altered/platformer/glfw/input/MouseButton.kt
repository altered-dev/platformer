package me.altered.platformer.glfw.input

import me.altered.platformer.glfw.IntEnum
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_3
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_4
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_5
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_6
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_7
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_8

enum class MouseButton(override val code: Int) : IntEnum {
    N1(GLFW_MOUSE_BUTTON_1),
    N2(GLFW_MOUSE_BUTTON_2),
    N3(GLFW_MOUSE_BUTTON_3),
    N4(GLFW_MOUSE_BUTTON_4),
    N5(GLFW_MOUSE_BUTTON_5),
    N6(GLFW_MOUSE_BUTTON_6),
    N7(GLFW_MOUSE_BUTTON_7),
    N8(GLFW_MOUSE_BUTTON_8),
    ;
    companion object {
        val LAST get() = N8
        val LEFT get() = N1
        val RIGHT get() = N2
        val MIDDLE get() = N3
    }
}
