package me.altered.platformer.level.data

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class CollisionFlags private constructor(private val value: UByte) {

    val left: Boolean
        get() = value and 0b0001u != 0.toUByte()

    val top: Boolean
        get() = value and 0b0010u != 0.toUByte()

    val right: Boolean
        get() = value and 0b0100u != 0.toUByte()

    val bottom: Boolean
        get() = value and 0b1000u != 0.toUByte()

    val all: Boolean
        get() = value == 0b1111u.toUByte()

    val none: Boolean
        get() = value == 0.toUByte()

    constructor(
        left: Boolean = false,
        top: Boolean = false,
        right: Boolean = false,
        bottom: Boolean = false,
    ) : this(
        left.toUByte() or
        top.toUByte().rotateLeft(1) or
        right.toUByte().rotateLeft(2) or
        bottom.toUByte().rotateLeft(3)
    )

    constructor(
        all: Boolean,
    ) : this(if (all) 0b1111u.toUByte() else 0.toUByte())
}

private fun Boolean.toUByte() = (if (this) 1 else 0).toUByte()
