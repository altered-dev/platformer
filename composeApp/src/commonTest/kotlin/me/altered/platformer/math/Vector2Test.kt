package me.altered.platformer.math

import kotlin.test.Test

class Vector2Test {

    @Test
    fun test() {
        val x = 4234234.0f
        val y = 120319.0f
        val z = (x.toRawBits().toLong() shl 32) + y.toRawBits()

        println(x.toRawBits().toString(2).padStart(32, '0'))
        println(" ".repeat(32) + y.toRawBits().toString(2).padStart(32, '0'))
        println(z.toString(2).padStart(64, '0'))
        println(Float.fromBits(z.toInt()).toBits().toString(2).padStart(32, '0'))
    }
}
