package me.altered.platformer.util

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private class EventDelegate<T>(var value: T) : ReadOnlyProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }
}

fun <TR> Event0.delegate(initialValue: TR, setter: () -> TR): ReadOnlyProperty<Any?, TR> {
    val delegate = EventDelegate(initialValue)
    this += { delegate.value = setter() }
    return delegate
}

fun <T1, TR> Event1<T1>.delegate(initialValue: TR, setter: (T1) -> TR): ReadOnlyProperty<Any?, TR> {
    val delegate = EventDelegate(initialValue)
    this += { arg1 -> delegate.value = setter(arg1) }
    return delegate
}

fun <T1, T2, TR> Event2<T1, T2>.delegate(initialValue: TR, setter: (T1, T2) -> TR): ReadOnlyProperty<Any?, TR> {
    val delegate = EventDelegate(initialValue)
    this += { arg1, arg2 -> delegate.value = setter(arg1, arg2) }
    return delegate
}
