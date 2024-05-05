package me.altered.platformer.ui

import io.github.humbleui.skija.Canvas
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class Widget(
    width: Size,
    height: Size,
    margin: Insets,
) {

    var width by prop(width)
    var height by prop(height)
    var margin by prop(margin)

    private var measured = false
    private var lastWidth: Float? = null
    private var lastHeight: Float? = null

    fun measure(boundWidth: Float, boundHeight: Float) {
        if (measured && boundWidth == lastWidth && boundHeight == lastHeight) return

        val widthPx = when (val width = width) {
            is Size.Fixed -> width.value
            Size.Wrap -> 0.0f // TODO: implement wrapping
            Size.Expand -> boundWidth - margin.horizontal
        }
        val heightPx = when (val height = height) {
            is Size.Fixed -> height.value
            Size.Wrap -> 0.0f // TODO: implement wrapping
            Size.Expand -> boundHeight - margin.vertical
        }

        if (this is WidgetGroup) {
            // TODO: measure children
        }

        measured = true
        lastWidth = boundWidth
        lastHeight = boundHeight
    }

    abstract fun draw(canvas: Canvas)

    protected fun <T> prop(value: T): ReadWriteProperty<Widget, T> = Property(value)

    private inner class Property<T>(private var value: T): ReadWriteProperty<Widget, T> {

        override fun getValue(thisRef: Widget, property: KProperty<*>): T {
            return value
        }

        override fun setValue(thisRef: Widget, property: KProperty<*>, value: T) {
            this.value = value
            measured = false
        }
    }
}
