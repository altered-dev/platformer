package me.altered.platformer.level

import me.altered.platformer.timeline.Expression
import kotlin.reflect.KProperty

interface TimeContext {

    val time: Float
}

operator fun <T> Expression<T>.getValue(thisRef: TimeContext, prop: KProperty<*>): T {
    return eval(thisRef.time)
}
