@file:JvmName("Main")

package me.altered.platformer

import io.github.humbleui.skija.BackendRenderTarget
import io.github.humbleui.skija.ColorSpace
import io.github.humbleui.skija.DirectContext
import io.github.humbleui.skija.FramebufferFormat
import io.github.humbleui.skija.Surface
import io.github.humbleui.skija.SurfaceColorFormat
import io.github.humbleui.skija.SurfaceOrigin
import me.altered.platformer.glfw.createWindow
import me.altered.platformer.glfw.enumValueOf
import me.altered.platformer.glfw.glfw
import me.altered.platformer.glfw.input.InputEvent
import me.altered.platformer.glfw.input.KeyMod
import me.altered.platformer.scene.SceneManager
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.clear
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11

fun main() = glfw {
    val width = 800
    val height = 600
    val targetFps = 165
    val targetUps = 60

    val window = createWindow(width, height, "average platformer experience") {
        visible = false
        resizable = true
    }
    checkNotNull(window) { "Failed to create GLFW window." }
    window.makeContextCurrent()
    window.setSwapInterval(1)
    window.show()

    GL.createCapabilities()

    val context = DirectContext.makeGL()
    var target = makeTarget(width, height)
    var surface = makeSurface(context, target)
    var canvas = surface.canvas

    window.onResize += { w, h ->
        target.close()
        surface.close()
        target = makeTarget(w, h)
        surface = makeSurface(context, target)
        canvas = surface.canvas
    }

    window.onKey += { key, scancode, action, mods ->
        SceneManager.input(InputEvent.Key(enumValueOf(key), scancode, enumValueOf(action), KeyMod(mods)))
    }
    window.onMouseButton += { button, action, mods ->
        SceneManager.input(InputEvent.MouseButton(enumValueOf(button), enumValueOf(action), KeyMod(mods)))
    }
    window.onCursorMove += { x, y -> SceneManager.input(InputEvent.CursorMove(x, y)) }
    window.onCursorEnter += { entered -> SceneManager.input(InputEvent.CursorEnter(entered)) }
    window.onScroll += { dx, dy -> SceneManager.input(InputEvent.Scroll(dx, dy)) }

    SceneManager.setScene(MainScene())

    var initialTime = System.currentTimeMillis()
    val timeU = 1000.0f / targetUps
    val timeR = if (targetFps > 0) 1000.0f / targetFps else 0.0f
    var updateTime = initialTime
    var deltaUpdate = 0.0f
    var deltaFps = 0.0f

    while (!window.shouldClose) {
        window.pollEvents()

        val now = System.currentTimeMillis()
        deltaUpdate += (now - initialTime) / timeU
        deltaFps += (now - initialTime) / timeR

        if (deltaUpdate >= 1) {
            SceneManager.update((now - updateTime) * 0.001f)
            updateTime = now
            deltaUpdate--
        }

        if (targetFps <= 0 || deltaFps >= 1) {
            canvas.clear(Color.white)
            SceneManager.draw(canvas)
            context.flush()
            deltaFps--
        }

        initialTime = now
        window.swapBuffers()
    }

    SceneManager.destroyScene()
}

private fun makeTarget(width: Int, height: Int): BackendRenderTarget {
    // TODO: detect rendering platform
    return BackendRenderTarget.makeGL(
        /* width = */ width,
        /* height = */ height,
        /* sampleCnt = */ 0,
        /* stencilBits = */ 8,
        /* fbId = */ GL11.glGetInteger(0x8CA6),
        /* fbFormat = */ FramebufferFormat.GR_GL_RGBA8,
    )
}

private fun makeSurface(context: DirectContext, target: BackendRenderTarget): Surface {
    return Surface.wrapBackendRenderTarget(
        /* context = */ context,
        /* rt = */ target,
        /* origin = */ SurfaceOrigin.BOTTOM_LEFT,
        /* colorFormat = */ SurfaceColorFormat.RGBA_8888,
        /* colorSpace = */ ColorSpace.getSRGB(),
    )
}
