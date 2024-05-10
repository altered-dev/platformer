package me.altered.platformer.glfw.window

import me.altered.platformer.glfw.IntEnum
import org.lwjgl.glfw.GLFW.*
import kotlin.reflect.KProperty

@Suppress("NOTHING_TO_INLINE")
object WindowHints {

    const val dontCare = GLFW_DONT_CARE
    const val anyPosition = GLFW_ANY_POSITION

    var resizable by bool(GLFW_RESIZABLE)
    var visible by bool(GLFW_VISIBLE)
    var decorated by bool(GLFW_DECORATED)
    var focused by bool(GLFW_FOCUSED)
    var autoIconify by bool(GLFW_AUTO_ICONIFY)
    var floating by bool(GLFW_FLOATING)
    var maximized by bool(GLFW_MAXIMIZED)
    var centerCursor by bool(GLFW_CENTER_CURSOR)
    var transparentFramebuffer by bool(GLFW_TRANSPARENT_FRAMEBUFFER)
    var focusOnShow by bool(GLFW_FOCUS_ON_SHOW)
    var scaleToMonitor by bool(GLFW_SCALE_TO_MONITOR)
    var mousePassthrough by bool(GLFW_MOUSE_PASSTHROUGH)
    var positionX by int(GLFW_POSITION_X)
    var positionY by int(GLFW_POSITION_Y)
    var redBits by int(GLFW_RED_BITS)
    var greenBits by int(GLFW_GREEN_BITS)
    var blueBits by int(GLFW_BLUE_BITS)
    var alphaBits by int(GLFW_ALPHA_BITS)
    var depthBits by int(GLFW_DEPTH_BITS)
    var stencilBits by int(GLFW_STENCIL_BITS)
    var accumRedBits by int(GLFW_ACCUM_RED_BITS)
    var accumGreenBits by int(GLFW_ACCUM_GREEN_BITS)
    var accumBlueBits by int(GLFW_ACCUM_BLUE_BITS)
    var accumAlphaBits by int(GLFW_ACCUM_ALPHA_BITS)
    var auxBuffers by int(GLFW_AUX_BUFFERS)
    var samples by int(GLFW_SAMPLES)
    var refreshRate by int(GLFW_REFRESH_RATE)
    var stereo by bool(GLFW_STEREO)
    var srgbCapable by bool(GLFW_SRGB_CAPABLE)
    var doubleBuffer by bool(GLFW_DOUBLEBUFFER)
    var clientApi by enum<ClientApi>(GLFW_CLIENT_API)
    var contextCreationApi by enum<ContextCreationApi>(GLFW_CONTEXT_CREATION_API)
    var contextVersionMajor by int(GLFW_CONTEXT_VERSION_MAJOR)
    var contextVersionMinor by int(GLFW_CONTEXT_VERSION_MINOR)
    var contextRobustness by enum<ContextRobustness>(GLFW_CONTEXT_ROBUSTNESS)
    var contextReleaseBehavior by enum<ReleaseBehavior>(GLFW_CONTEXT_RELEASE_BEHAVIOR)
    var openglForwardCompat by bool(GLFW_OPENGL_FORWARD_COMPAT)
    var contextDebug by bool(GLFW_CONTEXT_DEBUG)
    var openglDebugContext by bool(GLFW_OPENGL_DEBUG_CONTEXT)
    var openglProfile by enum<OpenGLProfile>(GLFW_OPENGL_PROFILE)
    var win32KeyboardMenu by bool(GLFW_WIN32_KEYBOARD_MENU)
    var cocoaFrameName by string(GLFW_COCOA_FRAME_NAME)
    var cocoaGraphicsSwitching by bool(GLFW_COCOA_GRAPHICS_SWITCHING)
    var waylandAppId by string(GLFW_WAYLAND_APP_ID)
    var x11ClassName by string(GLFW_X11_CLASS_NAME)
    var x11InstanceName by string(GLFW_X11_INSTANCE_NAME)

    @JvmInline
    private value class Hint<T : Any>(val hint: Int)

    private inline fun bool(hint: Int) = Hint<Boolean>(hint)
    private inline fun int(hint: Int) = Hint<Int>(hint)
    private inline fun string(hint: Int) = Hint<String>(hint)
    private inline fun <T> enum(hint: Int) where T : Enum<T>, T : IntEnum = Hint<T>(hint)

    private inline operator fun Hint<Boolean>.getValue(hints: WindowHints, prop: KProperty<*>): Boolean {
        error("Getting window hints is not supported.")
    }

    private inline operator fun Hint<Boolean>.setValue(hints: WindowHints, prop: KProperty<*>, value: Boolean) {
        glfwWindowHint(hint, if (value) GLFW_TRUE else GLFW_FALSE)
    }

    private inline operator fun Hint<Int>.getValue(hints: WindowHints, prop: KProperty<*>): Int {
        error("Getting window hints is not supported.")
    }

    private inline operator fun Hint<Int>.setValue(hints: WindowHints, prop: KProperty<*>, value: Int) {
        glfwWindowHint(hint, value)
    }

    private inline operator fun Hint<String>.getValue(hints: WindowHints, prop: KProperty<*>): String {
        error("Getting window hints is not supported.")
    }

    private inline operator fun Hint<String>.setValue(hints: WindowHints, prop: KProperty<*>, value: String) {
        glfwWindowHintString(hint, value)
    }

    private inline operator fun <reified T> Hint<T>.getValue(hints: WindowHints, prop: KProperty<*>): T where T : Enum<T>, T : IntEnum {
        error("Getting window hints is not supported.")
    }

    private inline operator fun <reified T> Hint<T>.setValue(hints: WindowHints, prop: KProperty<*>, value: T) where T : Enum<T>, T : IntEnum {
        glfwWindowHint(hint, value.code)
    }
}
