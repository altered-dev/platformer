package me.altered.platformer.level

import me.altered.platformer.level.data.Object

interface WorldContext : ObjectContainer, TimeContext {

    fun reference(vararg path: String): Object
}
