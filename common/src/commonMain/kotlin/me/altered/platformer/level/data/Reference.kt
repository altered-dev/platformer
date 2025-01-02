package me.altered.platformer.level.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * A reference to an object in a level.
 */
@Serializable
@SerialName("reference")
@JvmInline
value class Reference(val id: Long) {

    fun resolve(level: Level) {

    }
}