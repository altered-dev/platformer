package me.altered.platformer.engine.ui

import me.altered.platformer.engine.node.CanvasNode
import me.altered.platformer.engine.node.Node
import me.altered.platformer.engine.util.observable
import org.jetbrains.skia.Rect
import kotlin.properties.ReadWriteProperty

abstract class UiNode2(
    name: String = "UiNode",
    parent: Node? = null,
    width: Size,
    height: Size,
) : CanvasNode(name, parent) {

    var width by layoutProp(width)
    var height by layoutProp(height)

    var bounds = Rect.makeWH(0.0f, 0.0f)
        protected set

    fun invalidateLayout() {

    }

    open fun measure() {

    }

    protected fun <T> layoutProp(initialValue: T): ReadWriteProperty<UiNode2, T> =
        observable(initialValue) { invalidateLayout() }
}