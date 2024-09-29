package me.altered.platformer.engine.ui

import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.node.Node
import org.jetbrains.skia.Shader

open class Button(
    name: String = "Button",
    parent: Node? = null,
    var onClick: (Button) -> Unit = {},
    width: Size = wrap(min = 72.0f),
    height: Size = wrap(min = 36.0f),
    cornerRadius: Float = 0.0f,
    padding: Insets = padding(),
    horizontalAlignment: Alignment = center,
    verticalAlignment: Alignment = center,
    spacing: Float = 0.0f,
    fill: Shader = Shader.makeEmpty(),
    stroke: Shader = Shader.makeEmpty(),
    strokeWidth: Float = 0.0f,
) : Row(name, parent, width, height, cornerRadius, padding, horizontalAlignment, verticalAlignment, spacing, fill, stroke, strokeWidth) {

    private val text = +Text(name, fill = Shader.makeColor(Color.Black.value))

    override var name by text::name

    override fun onHover(hovered: Boolean) {
        stroke = if (hovered) Shader.makeColor(Color(0xFF262626).value) else Shader.makeEmpty()
    }

    override fun onClick(clicked: Boolean) {
        if (isHovered && !clicked) {
            onClick(this)
        }
    }
}
