package me.altered.platformer.glfw

interface InputHandler {

    fun onResize(width: Int, height: Int)

    fun onKey(key: Int, scancode: Int, action: Int, mods: Int)

    fun onMouseButton(button: Int, action: Int, mods: Int)

    fun onCursorMove(x: Double, y: Double)

    fun onCursorEnter(entered: Boolean)

    fun onScroll(dx: Double, dy: Double)
}
