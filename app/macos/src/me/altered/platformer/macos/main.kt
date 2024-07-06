package me.altered.platformer.macos

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import me.altered.platformer.MainScene
import me.altered.platformer.engine.input.Action
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.Left
import me.altered.platformer.engine.input.Modifier
import me.altered.platformer.engine.input.MouseButton
import me.altered.platformer.engine.node.SceneManager
import me.altered.platformer.engine.util.enumValueOf
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import platform.AppKit.NSApplication
import platform.AppKit.NSApplicationActivationPolicy
import platform.AppKit.NSApplicationDelegateProtocol
import platform.AppKit.NSBackingStoreBuffered
import platform.AppKit.NSEvent
import platform.AppKit.NSMenu
import platform.AppKit.NSTrackingActiveInActiveApp
import platform.AppKit.NSTrackingArea
import platform.AppKit.NSTrackingMouseMoved
import platform.AppKit.NSView
import platform.AppKit.NSViewHeightSizable
import platform.AppKit.NSViewWidthSizable
import platform.AppKit.NSWindow
import platform.AppKit.NSWindowStyleMaskClosable
import platform.AppKit.NSWindowStyleMaskMiniaturizable
import platform.AppKit.NSWindowStyleMaskResizable
import platform.AppKit.NSWindowStyleMaskTitled
import platform.Foundation.NSMakeRect
import platform.Foundation.NSSelectorFromString
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
fun main() {
    val app = NSApplication.sharedApplication()
    app.setActivationPolicy(NSApplicationActivationPolicy.NSApplicationActivationPolicyRegular)
    val appName = "hello lol macos lmao wow"
    val bar = NSMenu()
    app.setMainMenu(bar)
    val appMenuItem = bar.addItemWithTitle(appName, null, "")
    val appMenu = NSMenu()
    appMenuItem.setSubmenu(appMenu)
    appMenu.addItemWithTitle("About $appName", NSSelectorFromString("orderFrontStandardAboutPanel:"), "a")
    appMenu.addItemWithTitle("Quit $appName", NSSelectorFromString("terminate:"), "q")

    app.delegate = object : NSObject(), NSApplicationDelegateProtocol {
        override fun applicationShouldTerminateAfterLastWindowClosed(sender: NSApplication): Boolean {
            return true
        }
    }
    val windowStyle = NSWindowStyleMaskTitled or
        NSWindowStyleMaskMiniaturizable or
        NSWindowStyleMaskClosable or
        NSWindowStyleMaskResizable
    val window = object : NSWindow(
        contentRect = NSMakeRect(0.0, 0.0, 640.0, 480.0),
        styleMask = windowStyle,
        backing = NSBackingStoreBuffered,
        defer = false
    ) {
        override fun canBecomeKeyWindow() = true
        override fun canBecomeMainWindow() = true
    }
    window.title = appName
    val skiaLayer = SkiaLayer()
    val sceneManager = SceneManager
    skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, sceneManager)

    val nsView = object : NSView(window.contentView!!.bounds) {
        private var trackingArea : NSTrackingArea? = null

        override fun updateTrackingAreas() {
            trackingArea?.let { removeTrackingArea(it) }
            trackingArea = NSTrackingArea(
                rect = bounds,
                options = NSTrackingActiveInActiveApp or NSTrackingMouseMoved,
                owner = this,
                userInfo = null
            )
            addTrackingArea(trackingArea!!)
        }

        override fun acceptsFirstResponder(): Boolean {
            return true
        }

        override fun mouseMoved(event: NSEvent) {
            val height = frame.useContents { size.height }
            event.locationInWindow.useContents {
                sceneManager.input(InputEvent.CursorMove(x.toFloat(), (height - y).toFloat()))
            }
        }

        override fun mouseDown(event: NSEvent) {
            val height = frame.useContents { size.height }
            event.locationInWindow.useContents {
                sceneManager.input(InputEvent.MouseEvent(
                    button = MouseButton.Left,
                    x = x.toFloat(),
                    y = (height - y).toFloat(),
                    action = Action.Press,
                    modifier = Modifier.None,
                ))
            }
        }

        override fun mouseUp(event: NSEvent) {
            val height = frame.useContents { size.height }
            event.locationInWindow.useContents {
                sceneManager.input(InputEvent.MouseEvent(
                    button = MouseButton.Left,
                    x = x.toFloat(),
                    y = (height - y).toFloat(),
                    action = Action.Release,
                    modifier = Modifier.None,
                ))
            }
        }

        override fun keyDown(event: NSEvent) {
            sceneManager.input(
                InputEvent.KeyEvent(
                    key = enumValueOf(event.keyCode.toInt()),
                    action = Action.Press,
                    modifier = Modifier(event.modifierFlags)
                )
            )
        }

        override fun keyUp(event: NSEvent) {
            sceneManager.input(
                InputEvent.KeyEvent(
                    key = enumValueOf(event.keyCode.toInt()),
                    action = Action.Release,
                    modifier = Modifier(event.modifierFlags)
                )
            )
        }

        override fun scrollWheel(event: NSEvent) {
            sceneManager.input(
                InputEvent.Scroll(
                    dx = event.scrollingDeltaX.toFloat(),
                    dy = event.scrollingDeltaY.toFloat(),
                )
            )
        }
    }

    nsView.autoresizingMask = NSViewHeightSizable or NSViewWidthSizable
    val contentView = window.contentView!!
    contentView.addSubview(nsView)
    skiaLayer.attachTo(nsView)
    sceneManager.scene = MainScene()

    window.makeKeyAndOrderFront(app)
    app.run()
}
