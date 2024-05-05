package me.altered.platformer.glfw

import org.lwjgl.glfw.GLFW.GLFW_CROSSHAIR_CURSOR
import org.lwjgl.glfw.GLFW.glfwCreateStandardCursor
import org.lwjgl.glfw.GLFW.glfwDestroyWindow
import org.lwjgl.glfw.GLFW.glfwGetFramebufferSize
import org.lwjgl.glfw.GLFW.glfwGetWindowContentScale
import org.lwjgl.glfw.GLFW.glfwGetWindowPos
import org.lwjgl.glfw.GLFW.glfwGetWindowSize
import org.lwjgl.glfw.GLFW.glfwHideWindow
import org.lwjgl.glfw.GLFW.glfwIconifyWindow
import org.lwjgl.glfw.GLFW.glfwMakeContextCurrent
import org.lwjgl.glfw.GLFW.glfwPollEvents
import org.lwjgl.glfw.GLFW.glfwRestoreWindow
import org.lwjgl.glfw.GLFW.glfwSetCursor
import org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback
import org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback
import org.lwjgl.glfw.GLFW.glfwSetKeyCallback
import org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback
import org.lwjgl.glfw.GLFW.glfwSetScrollCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowPos
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

    val contentScale: Pair<Float, Float>
        get() = memoryStack {
            val x = callocFloat(1)
            val y = callocFloat(1)
            glfwGetWindowContentScale(handle, x, y)
            x[0] to y[0]
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

    var inputHandler: InputHandler? = null
        set(value) {
            field = value
            glfwSetKeyCallback(handle, value?.let {{ _, k, s, a, m -> it.onKey(k, s, a, m) }})
            glfwSetMouseButtonCallback(handle, value?.let {{ _, b, a, m -> it.onMouseButton(b, a, m) }})
            glfwSetCursorPosCallback(handle, value?.let {{ _, x, y -> it.onCursorMove(x, y) }})
            glfwSetCursorEnterCallback(handle, value?.let {{ _, e -> it.onCursorEnter(e) }})
            glfwSetScrollCallback(handle, value?.let {{ _, x, y -> it.onScroll(x, y) }})
        }

    var onResize: ((Int, Int) -> Unit)? = null

    init {
        // temporary hack to have both listeners
        glfwSetWindowSizeCallback(handle) { _, w, h ->
            onResize?.invoke(w, h)
            inputHandler?.onResize(w, h)
        }
    }
}
