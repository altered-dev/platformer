package me.altered.platformer.editor

import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.ui.Button
import me.altered.platformer.engine.ui.Row
import me.altered.platformer.engine.ui.center
import me.altered.platformer.engine.ui.expand
import me.altered.platformer.engine.ui.padding
import me.altered.platformer.engine.ui.px
import me.altered.platformer.engine.ui.start
import org.jetbrains.skia.Shader

class TopPanel : Row(
    name = "top panel",
    width = expand(),
    height = 48.px,
    padding = padding(horizontal = 8.0f),
    horizontalAlignment = start,
    verticalAlignment = center,
    fill = Shader.makeColor(Color(0xFF333333).value),
) {
    var tool: Tool = Tool.Cursor
        set(value) {
            field = value
            tools.forEach { (tool, button) ->
                button.fill = if (tool == value) {
                    Shader.makeColor(Color(0xFFB2FFB2).value)
                } else {
                    Shader.makeEmpty()
                }
            }
        }

    private val tools = Tool.entries.associateWith { tool ->
        +Button(
            name = tool.name.substring(0, 1),
            width = 36.px,
            height = 36.px,
            onClick = { this.tool = tool }
        )
    }

    init {
        tool = Tool.Cursor
    }

    enum class Tool { Cursor, Pen, Rectangle, Ellipse, Text }
}