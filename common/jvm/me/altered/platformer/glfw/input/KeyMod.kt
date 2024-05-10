package me.altered.platformer.glfw.input

import org.lwjgl.glfw.GLFW.GLFW_MOD_ALT
import org.lwjgl.glfw.GLFW.GLFW_MOD_CAPS_LOCK
import org.lwjgl.glfw.GLFW.GLFW_MOD_CONTROL
import org.lwjgl.glfw.GLFW.GLFW_MOD_NUM_LOCK
import org.lwjgl.glfw.GLFW.GLFW_MOD_SHIFT
import org.lwjgl.glfw.GLFW.GLFW_MOD_SUPER

@JvmInline
value class KeyMod(private val value: Int) {

    operator fun plus(other: KeyMod) = KeyMod(value or other.value)

    infix fun has(other: KeyMod) = other.value == 0 || value and other.value != 0

    companion object {
        val NONE = KeyMod(0)
        val SHIFT = KeyMod(GLFW_MOD_SHIFT)
        val CONTROL = KeyMod(GLFW_MOD_CONTROL)
        val ALT = KeyMod(GLFW_MOD_ALT)
        val SUPER = KeyMod(GLFW_MOD_SUPER)
        val CAPS_LOCK = KeyMod(GLFW_MOD_CAPS_LOCK)
        val NUM_LOCK = KeyMod(GLFW_MOD_NUM_LOCK)
    }
}
