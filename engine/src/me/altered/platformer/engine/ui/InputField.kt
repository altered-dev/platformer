package me.altered.platformer.engine.ui

import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.input.scrolled
import me.altered.platformer.engine.node.Node
import org.jetbrains.skia.Shader

class InputField(
    name: String = "InputField",
    parent: Node? = null,
    value: String = "",
    var onValueChange: (value: String) -> Unit = {},
    var isNumeric: Boolean = true, // TODO: make false
    width: Size = expand(),
    height: Size = 36.px,
    cornerRadius: Float = 4.0f,
    padding: Insets = padding(all = 8.0f),
    horizontalAlignment: Alignment = start,
    verticalAlignment: Alignment = center,
    spacing: Float = 0.0f,
    fill: Shader = Shader.makeEmpty(),
    stroke: Shader = Shader.makeColor(Color(0xFF262626).value),
    strokeWidth: Float = 0.0f,
) : Row(name, parent, width, height, cornerRadius, padding, horizontalAlignment, verticalAlignment, spacing, fill, stroke, strokeWidth) {

    var value: String = validate(value); set(value) {
        if (isNumeric && value.toFloatOrNull() == null) return
        field = value
        text.name = value
        onValueChange(value)
    }

    private val text = +Text(this.value, width = expand(), fill = Shader.makeColor(Color.White.value))

    override fun input(event: InputEvent) {
        super.input(event)
        if (isNumeric && isHovered && event.scrolled()) {
            value = (value.toFloat() - event.dy * 0.1f).toString()
        }
    }

    private fun validate(value: String): String {
        return if (isNumeric && value.toFloatOrNull() == null) "0" else value
    }
}