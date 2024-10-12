package me.altered.platformer.editor

import me.altered.platformer.engine.graphics.Color
import me.altered.platformer.engine.ui.Column
import me.altered.platformer.engine.ui.InputField
import me.altered.platformer.engine.ui.Row
import me.altered.platformer.engine.ui.center
import me.altered.platformer.engine.ui.expand
import me.altered.platformer.engine.ui.padding
import me.altered.platformer.engine.ui.px
import me.altered.platformer.engine.ui.start
import me.altered.platformer.engine.ui.wrap
import me.altered.platformer.level.node.ObjectNode
import me.altered.platformer.level.node.RectangleNode
import org.jetbrains.skia.Rect
import org.jetbrains.skia.Shader

class Inspector : Column(
    name = "inspector",
    width = 256.px,
    height = expand(),
    padding = padding(all = 8.0f),
    horizontalAlignment = center,
    verticalAlignment = start,
    spacing = 16.0f,
    fill = Shader.makeColor(Color(0xFF333333).value),
) {

    var obj: ObjectNode<*>? = null; set(value) {
        if (field == value) return
        field = value
        removeChildren(children)
        if (value == null) return
        setup(value)
    }

    private fun setup(obj: ObjectNode<*>) {
        +Column(
            spacing = 8.0f,
        ).apply {
            +Row(
                height = wrap(),
                spacing = 8.0f,
            ).apply {
                +InputField(
                    name = "x",
                    width = 116.px,
                    value = obj.position.x.toString(),
                    onValueChange = { obj.position.x = it.toFloat() },
                )
                +InputField(
                    name = "y",
                    width = 116.px,
                    value = obj.position.y.toString(),
                    onValueChange = { obj.position.y = it.toFloat() },
                )
            }

            +Row(
                height = wrap(),
                spacing = 8.0f,
            ).apply {
                +InputField(
                    name = "w",
                    width = 116.px,
                    value = obj.bounds.width.toString(),
                    onValueChange = { obj.bounds = baseBounds.scale(it.toFloat(), obj.bounds.height) },
                )
                +InputField(
                    name = "h",
                    width = 116.px,
                    value = obj.bounds.height.toString(),
                    onValueChange = { obj.bounds = baseBounds.scale(obj.bounds.width, it.toFloat()) },
                )
            }

            +Row(
                height = wrap(),
                spacing = 8.0f,
            ).apply {
                +InputField(
                    name = "rotation",
                    width = 116.px,
                    value = obj.rotation.toString(),
                    onValueChange = { obj.rotation = it.toFloat() },
                )
                if (obj is RectangleNode) {
                    +InputField(
                        name = "radius",
                        width = 116.px,
                        value = obj.cornerRadius.toString(),
                        onValueChange = { obj.cornerRadius = it.toFloat() },
                    )
                }
            }
        }
    }

    companion object {

        // TODO: make a function for changing object size
        private val baseBounds = Rect(-0.5f, -0.5f, 0.5f, 0.5f)
    }
}