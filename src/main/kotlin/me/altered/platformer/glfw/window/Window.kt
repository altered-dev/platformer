package me.altered.platformer.glfw.window

import me.altered.platformer.glfw.IntEnum
import me.altered.platformer.glfw.enumValueOf
import me.altered.platformer.glfw.input.Cursor
import me.altered.platformer.glfw.input.Key
import me.altered.platformer.glfw.input.MouseButton
import me.altered.platformer.glfw.memoryStack
import org.lwjgl.glfw.GLFW.GLFW_AUTO_ICONIFY
import org.lwjgl.glfw.GLFW.GLFW_CLIENT_API
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_CREATION_API
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_DEBUG
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_NO_ERROR
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_RELEASE_BEHAVIOR
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_REVISION
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_ROBUSTNESS
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR
import org.lwjgl.glfw.GLFW.GLFW_DECORATED
import org.lwjgl.glfw.GLFW.GLFW_DOUBLEBUFFER
import org.lwjgl.glfw.GLFW.GLFW_FALSE
import org.lwjgl.glfw.GLFW.GLFW_FLOATING
import org.lwjgl.glfw.GLFW.GLFW_FOCUSED
import org.lwjgl.glfw.GLFW.GLFW_FOCUS_ON_SHOW
import org.lwjgl.glfw.GLFW.GLFW_HOVERED
import org.lwjgl.glfw.GLFW.GLFW_ICONIFIED
import org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_PASSTHROUGH
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RESIZABLE
import org.lwjgl.glfw.GLFW.GLFW_TRANSPARENT_FRAMEBUFFER
import org.lwjgl.glfw.GLFW.GLFW_TRUE
import org.lwjgl.glfw.GLFW.GLFW_VISIBLE
import org.lwjgl.glfw.GLFW.glfwCreateStandardCursor
import org.lwjgl.glfw.GLFW.glfwDestroyWindow
import org.lwjgl.glfw.GLFW.glfwFocusWindow
import org.lwjgl.glfw.GLFW.glfwGetFramebufferSize
import org.lwjgl.glfw.GLFW.glfwGetKey
import org.lwjgl.glfw.GLFW.glfwGetMouseButton
import org.lwjgl.glfw.GLFW.glfwGetWindowAttrib
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
import org.lwjgl.glfw.GLFW.glfwSetWindowAttrib
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
import kotlin.reflect.KProperty

@Suppress("NOTHING_TO_INLINE")
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
    fun focus() = glfwFocusWindow(handle)
    fun show() = glfwShowWindow(handle)
    fun hide() = glfwHideWindow(handle)
    fun makeContextCurrent() = glfwMakeContextCurrent(handle)
    fun swapBuffers() = glfwSwapBuffers(handle)
    fun destroy() = glfwDestroyWindow(handle)
    fun setSwapInterval(interval: Int) = glfwSwapInterval(interval)
    fun pollEvents() = glfwPollEvents()
    fun waitEvents() = glfwWaitEvents()
    fun setCursor(cursor: Cursor) = glfwSetCursor(handle, glfwCreateStandardCursor(cursor.code))

    fun keyPressed(key: Key) = glfwGetKey(handle, key.code) == GLFW_PRESS
    fun mouseButtonPressed(button: MouseButton) = glfwGetMouseButton(handle, button.code) == GLFW_PRESS

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

    companion object Attributes {

        val Window.focused by bool(GLFW_FOCUSED)
        val Window.iconified by bool(GLFW_ICONIFIED)
        val Window.maximized by bool(GLFW_MAXIMIZED)
        val Window.hovered by bool(GLFW_HOVERED)
        val Window.visible by bool(GLFW_VISIBLE)
        var Window.resizable by bool(GLFW_RESIZABLE)
        var Window.decorated by bool(GLFW_DECORATED)
        var Window.autoIconify by bool(GLFW_AUTO_ICONIFY)
        var Window.floating by bool(GLFW_FLOATING)
        val Window.transparentFramebuffer by bool(GLFW_TRANSPARENT_FRAMEBUFFER)
        var Window.focusOnShow by bool(GLFW_FOCUS_ON_SHOW)
        var Window.mousePassthrough by bool(GLFW_MOUSE_PASSTHROUGH)

        val Window.clientApi by enum<ClientApi>(GLFW_CLIENT_API)
        val Window.contextCreationApi by enum<ContextCreationApi>(GLFW_CONTEXT_CREATION_API)
        val Window.contextVersionMajor by int(GLFW_CONTEXT_VERSION_MAJOR)
        val Window.contextVersionMinor by int(GLFW_CONTEXT_VERSION_MINOR)
        val Window.contextRevision by int(GLFW_CONTEXT_REVISION)
        val Window.openglForwardCompat by bool(GLFW_OPENGL_FORWARD_COMPAT)
        val Window.contextDebug by bool(GLFW_CONTEXT_DEBUG)
        val Window.openglProfile by enum<OpenGLProfile>(GLFW_OPENGL_PROFILE)
        val Window.contextReleaseBehavior by enum<ReleaseBehavior>(GLFW_CONTEXT_RELEASE_BEHAVIOR)
        val Window.contextNoError by bool(GLFW_CONTEXT_NO_ERROR)
        val Window.contextRobustness by enum<ContextRobustness>(GLFW_CONTEXT_ROBUSTNESS)
        val Window.doubleBuffer by bool(GLFW_DOUBLEBUFFER)

        @JvmInline
        private value class Attribute<T : Any>(val hint: Int)

        private inline fun bool(hint: Int) = Attribute<Boolean>(hint)
        private inline fun int(hint: Int) = Attribute<Int>(hint)
        private inline fun <T> enum(hint: Int) where T : Enum<T>, T : IntEnum = Attribute<T>(hint)

        private inline operator fun Attribute<Boolean>.getValue(window: Window, prop: KProperty<*>): Boolean {
            return glfwGetWindowAttrib(window.handle, hint) == GLFW_TRUE
        }

        private inline operator fun Attribute<Boolean>.setValue(window: Window, prop: KProperty<*>, value: Boolean) {
            glfwSetWindowAttrib(window.handle, hint, if (value) GLFW_TRUE else GLFW_FALSE)
        }

        private inline operator fun Attribute<Int>.getValue(window: Window, prop: KProperty<*>): Int {
            return glfwGetWindowAttrib(window.handle, hint)
        }

        private inline operator fun Attribute<Int>.setValue(window: Window, prop: KProperty<*>, value: Int) {
            glfwSetWindowAttrib(window.handle, hint, value)
        }

        private inline operator fun <reified T> Attribute<T>.getValue(window: Window, prop: KProperty<*>): T where T : Enum<T>, T : IntEnum {
            return enumValueOf(glfwGetWindowAttrib(window.handle, hint))
        }

        private inline operator fun <reified T> Attribute<T>.setValue(window: Window, prop: KProperty<*>, value: T) where T : Enum<T>, T : IntEnum {
            glfwSetWindowAttrib(window.handle, hint, value.code)
        }
    }
}
