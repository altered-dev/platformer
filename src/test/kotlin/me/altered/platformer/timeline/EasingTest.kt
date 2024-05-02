package me.altered.platformer.timeline

import kotlin.test.Test

class EasingTest {

    @Test
    fun testLerp() {
        val values = List(101) { it / 100.0f }
        val from = 10.0f
        val to = 50.0f

        values.forEach { t ->
            println(lerp(from, to, t, Easing.sineOut))
        }
    }

    @Test
    fun testAlerp() {
        val values = List(41) { it + 10.0f }

        val from = 10.0f
        val to = 50.0f

        values.forEach { v ->
            println(alerp(from, to, v))
        }
    }
}
