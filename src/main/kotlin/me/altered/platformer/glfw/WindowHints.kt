package me.altered.platformer.glfw

import me.altered.platformer.glfw.WindowHint.ACCUM_ALPHA_BITS
import me.altered.platformer.glfw.WindowHint.ACCUM_BLUE_BITS
import me.altered.platformer.glfw.WindowHint.ACCUM_GREEN_BITS
import me.altered.platformer.glfw.WindowHint.ACCUM_RED_BITS
import me.altered.platformer.glfw.WindowHint.ALPHA_BITS
import me.altered.platformer.glfw.WindowHint.AUX_BUFFERS
import me.altered.platformer.glfw.WindowHint.BLUE_BITS
import me.altered.platformer.glfw.WindowHint.CLIENT_API
import me.altered.platformer.glfw.WindowHint.CONTEXT_ROBUSTNESS
import me.altered.platformer.glfw.WindowHint.CONTEXT_VERSION_MAJOR
import me.altered.platformer.glfw.WindowHint.CONTEXT_VERSION_MINOR
import me.altered.platformer.glfw.WindowHint.DECORATED
import me.altered.platformer.glfw.WindowHint.DEPTH_BITS
import me.altered.platformer.glfw.WindowHint.GREEN_BITS
import me.altered.platformer.glfw.WindowHint.OPENGL_DEBUG_CONTEXT
import me.altered.platformer.glfw.WindowHint.OPENGL_FORWARD_COMPAT
import me.altered.platformer.glfw.WindowHint.OPENGL_PROFILE
import me.altered.platformer.glfw.WindowHint.RED_BITS
import me.altered.platformer.glfw.WindowHint.REFRESH_RATE
import me.altered.platformer.glfw.WindowHint.RESIZABLE
import me.altered.platformer.glfw.WindowHint.SAMPLES
import me.altered.platformer.glfw.WindowHint.SRGB_CAPABLE
import me.altered.platformer.glfw.WindowHint.STENCIL_BITS
import me.altered.platformer.glfw.WindowHint.STEREO
import me.altered.platformer.glfw.WindowHint.VISIBLE
import org.lwjgl.glfw.GLFW.GLFW_FALSE
import org.lwjgl.glfw.GLFW.GLFW_LOSE_CONTEXT_ON_RESET
import org.lwjgl.glfw.GLFW.GLFW_NO_RESET_NOTIFICATION
import org.lwjgl.glfw.GLFW.GLFW_NO_ROBUSTNESS
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_ANY_PROFILE
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_API
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_COMPAT_PROFILE
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_ES_API
import org.lwjgl.glfw.GLFW.GLFW_TRUE
import org.lwjgl.glfw.GLFW.glfwWindowHint
import org.lwjgl.glfw.GLFW.glfwWindowHintString
import kotlin.enums.enumEntries
import kotlin.reflect.KProperty
import me.altered.platformer.glfw.WindowHint as WindowHintEnum

@Suppress("NOTHING_TO_INLINE")
object WindowHints {
    var resizable by bool(RESIZABLE)
    var visible by bool(VISIBLE)
    var decorated by bool(DECORATED)
    var redBits by int(RED_BITS)
    var greenBits by int(GREEN_BITS)
    var blueBits by int(BLUE_BITS)
    var alphaBits by int(ALPHA_BITS)
    var depthBits by int(DEPTH_BITS)
    var stencilBits by int(STENCIL_BITS)
    var accumRedBits by int(ACCUM_RED_BITS)
    var accumGreenBits by int(ACCUM_GREEN_BITS)
    var accumBlueBits by int(ACCUM_BLUE_BITS)
    var accumAlphaBits by int(ACCUM_ALPHA_BITS)
    var auxBuffers by int(AUX_BUFFERS)
    var samples by int(SAMPLES)
    var refreshRate by int(REFRESH_RATE)
    var stereo by bool(STEREO)
    var srgbCapable by bool(SRGB_CAPABLE)
    var clientApi by enum<ClientApi>(CLIENT_API)
    var contextVersionMajor by int(CONTEXT_VERSION_MAJOR)
    var contextVersionMinor by int(CONTEXT_VERSION_MINOR)
    var contextRobustness by enum<ContextRobustness>(CONTEXT_ROBUSTNESS)
    var openglForwardCompat by bool(OPENGL_FORWARD_COMPAT)
    var openglDebugContext by bool(OPENGL_DEBUG_CONTEXT)
    var openglProfile by enum<OpenGLProfile>(OPENGL_PROFILE)

    enum class ClientApi(override val code: Int) : IntEnum {
        OPENGL_API(GLFW_OPENGL_API),
        OPENGL_ES_API(GLFW_OPENGL_ES_API),
    }

    enum class ContextRobustness(override val code: Int) : IntEnum {
        NO_ROBUSTNESS(GLFW_NO_ROBUSTNESS),
        NO_RESET_NOTIFICATION(GLFW_NO_RESET_NOTIFICATION),
        LOSE_CONTEXT_ON_RESET(GLFW_LOSE_CONTEXT_ON_RESET),
    }

    enum class OpenGLProfile(override val code: Int) : IntEnum {
        ANY_PROFILE(GLFW_OPENGL_ANY_PROFILE),
        COMPAT_PROFILE(GLFW_OPENGL_COMPAT_PROFILE),
        CORE_PROFILE(GLFW_OPENGL_CORE_PROFILE),
    }

    @JvmInline
    private value class WindowHint<T : Any>(val hint: Int)

    private inline fun bool(hint: WindowHintEnum) = WindowHint<Boolean>(hint.code)
    private inline fun int(hint: WindowHintEnum) = WindowHint<Int>(hint.code)
    private inline fun string(hint: WindowHintEnum) = WindowHint<String>(hint.code)
    private inline fun <T> enum(hint: WindowHintEnum) where T : Enum<T>, T : IntEnum = WindowHint<T>(hint.code)

    private inline operator fun WindowHint<Boolean>.getValue(hints: WindowHints, prop: KProperty<*>): Boolean {
        TODO("")
    }

    private inline operator fun WindowHint<Boolean>.setValue(hints: WindowHints, prop: KProperty<*>, value: Boolean) {
        glfwWindowHint(hint, if (value) GLFW_TRUE else GLFW_FALSE)
    }

    private inline operator fun WindowHint<Int>.getValue(hints: WindowHints, prop: KProperty<*>): Int {
        return hint
    }

    private inline operator fun WindowHint<Int>.setValue(hints: WindowHints, prop: KProperty<*>, value: Int) {
        glfwWindowHint(hint, value)
    }

    private inline operator fun WindowHint<String>.getValue(hints: WindowHints, prop: KProperty<*>): String {
        return hint.toString()
    }

    private inline operator fun WindowHint<String>.setValue(hints: WindowHints, prop: KProperty<*>, value: String) {
        glfwWindowHintString(hint, value)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private inline operator fun <reified T> WindowHint<T>.getValue(hints: WindowHints, prop: KProperty<*>): T where T : Enum<T>, T : IntEnum {
        return enumEntries<T>().first()
    }

    private inline operator fun <reified T> WindowHint<T>.setValue(hints: WindowHints, prop: KProperty<*>, value: T) where T : Enum<T>, T : IntEnum {
        glfwWindowHint(hint, value.code)
    }
}
