package me.altered.platformer

import io.github.humbleui.skija.BackendRenderTarget
import io.github.humbleui.skija.Canvas
import io.github.humbleui.skija.ColorSpace
import io.github.humbleui.skija.DirectContext
import io.github.humbleui.skija.FramebufferFormat
import io.github.humbleui.skija.Surface
import io.github.humbleui.skija.SurfaceColorFormat
import io.github.humbleui.skija.SurfaceOrigin
import io.github.humbleui.skija.impl.Stats
import me.altered.platformer.glfw.window.Window
import me.altered.platformer.glfw.window.createWindow
import me.altered.platformer.node.SceneManager
import me.altered.platformer.skija.Color
import me.altered.platformer.skija.clear
import org.lwjgl.glfw.GLFW.glfwInit
import org.lwjgl.glfw.GLFW.glfwTerminate
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11

object Main {

    private lateinit var window: Window

    private val context by lazy { DirectContext.makeGL() }
    private lateinit var canvas: Canvas

    private var targetFps = 0
    private var targetUps = 0

    /**
     * @param args
     * - `--width` / `-w`: window width (default: 800)
     * - `--height` / `-h`: window height (default: 600)
     * - `--title` / `-t`: window title (default: "")
     * - `--fps` / `-f`: target frames per second (default: uncapped)
     * - `--ups`/ `-u`: target updates per second (default: 60)
     * - `--vsync` / `-v`: enable v-sync
     * - `--enable-stats`: count skija native calls
     */
    @JvmStatic
    fun main(args: Array<String>) {
        Stats.enabled = args.getBoolean("enable-stats")
        targetFps = args.getInt("fps", "f", 0)
        targetUps = args.getInt("ups", "u", 60)

        try {
            check(glfwInit()) { "Failed to initialize GLFW." }
            initWindow(args)
            GL.createCapabilities()
            initSkia()
            SceneManager.scene = MainScene(window)
            loop()
            SceneManager.destroy()
            window.destroy()
        } finally {
            glfwTerminate()
        }
    }

    private fun initWindow(args: Array<String>) {
        window = createWindow(
            width = args.getInt("width", "w", 800),
            height = args.getInt("height", "h", 600),
            title = args.getString("title", "t", ""),
        ) {
            visible = false
            resizable = true
        } ?: error("Failed to create GLFW window.")

        window.makeContextCurrent()
        window.setSwapInterval(if (args.getBoolean("vsync", "v")) 1 else 0)
        window.show()
        window.inputHandler = SceneManager
        window.onResize = { _, _ -> initSkia() }
    }

    private fun initSkia() {
        val (width, height) = window.framebufferSize
        val (scaleX, scaleY) = window.contentScale

        val target = makeTarget(width, height)
        val surface = makeSurface(context, target)
        canvas = surface.canvas.scale(scaleX, scaleY)
    }

    private fun loop() {
        var initialTime = System.currentTimeMillis()
        val timeU = 1000.0f / targetUps
        val timeR = if (targetFps > 0) 1000.0f / targetFps else 0.0f
        var updateTime = initialTime
        var frameTime = initialTime
        var deltaUpdate = 0.0f
        var deltaFps = 0.0f

        while (!window.shouldClose) {
            window.pollEvents()

            val now = System.currentTimeMillis()
            deltaUpdate += (now - initialTime) / timeU
            deltaFps += (now - initialTime) / timeR

            if (deltaUpdate >= 1) {
                SceneManager.physicsUpdate((now - updateTime) * 0.001f)
                updateTime = now
                deltaUpdate--
            }

            if (targetFps <= 0 || deltaFps >= 1) {
                SceneManager.update((now - frameTime) * 0.001f)
                frameTime = now
                canvas.clear(Color.white)
                SceneManager.draw(canvas)
                context.flush()
                deltaFps--
            }

            SceneManager.postUpdate()
            initialTime = now
            window.swapBuffers()
        }
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
}
