package me.altered.platformer.glfw

import me.altered.platformer.util.glfwEvent
import org.lwjgl.glfw.GLFW.glfwDestroyWindow
import org.lwjgl.glfw.GLFW.glfwGetFramebufferSize
import org.lwjgl.glfw.GLFW.glfwGetWindowPos
import org.lwjgl.glfw.GLFW.glfwGetWindowSize
import org.lwjgl.glfw.GLFW.glfwHideWindow
import org.lwjgl.glfw.GLFW.glfwIconifyWindow
import org.lwjgl.glfw.GLFW.glfwMakeContextCurrent
import org.lwjgl.glfw.GLFW.glfwPollEvents
import org.lwjgl.glfw.GLFW.glfwRestoreWindow
import org.lwjgl.glfw.GLFW.glfwSetCharCallback
import org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback
import org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback
import org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback
import org.lwjgl.glfw.GLFW.glfwSetKeyCallback
import org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback
import org.lwjgl.glfw.GLFW.glfwSetScrollCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowFocusCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowIconifyCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowPos
import org.lwjgl.glfw.GLFW.glfwSetWindowPosCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowRefreshCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose
import org.lwjgl.glfw.GLFW.glfwSetWindowSize
import org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowTitle
import org.lwjgl.glfw.GLFW.glfwShowWindow
import org.lwjgl.glfw.GLFW.glfwSwapBuffers
import org.lwjgl.glfw.GLFW.glfwSwapInterval
import org.lwjgl.glfw.GLFW.glfwWaitEvents
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose

class Window(private val handle: Long) {

    var shouldClose: Boolean
        get() = glfwWindowShouldClose(handle)
        set(value) = glfwSetWindowShouldClose(handle, value)

    var position: Pair<Int, Int>
        get() = memoryStack {
            val x = callocInt(1)
            val y = callocInt(1)
            glfwGetWindowPos(this@Window.handle, x, y)
            x[0] to y[0]
        }
        set(value) = glfwSetWindowPos(handle, value.first, value.second)

    var size: Pair<Int, Int>
        get() = memoryStack {
            val w = callocInt(1)
            val h = callocInt(1)
            glfwGetWindowSize(this@Window.handle, w, h)
            w[0] to h[0]
        }
        set(value) = glfwSetWindowSize(handle, value.first, value.second)

    val framebufferSize: Pair<Int, Int>
        get() = memoryStack {
            val w = callocInt(1)
            val h = callocInt(1)
            glfwGetFramebufferSize(this@Window.handle, w, h)
            w[0] to h[0]
        }

    // TODO: to property
    fun setTitle(title: String) = glfwSetWindowTitle(handle, title)

    fun iconify() = glfwIconifyWindow(handle)
    fun restore() = glfwRestoreWindow(handle)
    fun show() = glfwShowWindow(handle)
    fun hide() = glfwHideWindow(handle)
    fun makeContextCurrent() = glfwMakeContextCurrent(handle)
    fun swapBuffers() = glfwSwapBuffers(handle)
    fun destroy() = glfwDestroyWindow(handle)
    fun setSwapInterval(interval: Int) = glfwSwapInterval(interval)
    fun pollEvents() = glfwPollEvents()
    fun waitEvents() = glfwWaitEvents()

    val onMove = glfwEvent<Int, Int> { glfwSetWindowPosCallback(handle, it) }
    val onResize = glfwEvent<Int, Int> { glfwSetWindowSizeCallback(handle, it) }
    val onClose = glfwEvent { glfwSetWindowCloseCallback(handle, it) }
    val onRefresh = glfwEvent { glfwSetWindowRefreshCallback(handle, it) }
    val onFocus = glfwEvent<Boolean> { glfwSetWindowFocusCallback(handle, it) }
    val onIconify = glfwEvent<Boolean> { glfwSetWindowIconifyCallback(handle, it) }
    val onFramebufferResize = glfwEvent<Int, Int> { glfwSetFramebufferSizeCallback(handle, it) }
    val onKey = glfwEvent<Int, Int, Int, Int> { glfwSetKeyCallback(handle, it) }
    val onChar = glfwEvent<Int> { glfwSetCharCallback(handle, it) }
    val onMouseButton = glfwEvent<Int, Int, Int> { glfwSetMouseButtonCallback(handle, it) }
    val onCursorMove = glfwEvent<Double, Double> { glfwSetCursorPosCallback(handle, it) }
    val onCursorEnter = glfwEvent<Boolean> { glfwSetCursorEnterCallback(handle, it) }
    val onScroll = glfwEvent<Double, Double> { glfwSetScrollCallback(handle, it) }
}
