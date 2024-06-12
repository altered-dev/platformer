package me.altered.platformer.engine.util

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <T> observable(initialValue: T, crossinline onUpdate: (T) -> Unit) = object : ReadWriteProperty<Any?, T> {

    private var value = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (this.value == value) return
        this.value = value
        onUpdate(value)
    }
}

fun <T> logged(initialValue: T) = object : ReadWriteProperty<Any?, T> {

    private var value = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (this.value == value) return
        println("value of '${property.name}' changed, old: ${this.value}, new: $value")
        this.value = value
    }
}
