package me.altered.platformer.timeline

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.function.ThrowingConsumer
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class EasingTest {

    @TestFactory
    fun `ease(0f) == 0f && ease(1f) == 1f`() = testEasings { easing ->
        assertAll(
            { assertEquals(0.0f, easing.ease(0.0f), Float.MIN_VALUE) },
            { assertEquals(1.0f, easing.ease(1.0f), Float.MIN_VALUE) },
        )
    }

    @TestFactory
    fun `ease(n) != NaN`() = testEasings { easing ->
        assertAll((0..100).map {{
            val value = it * 0.01f
            assertFalse(easing.ease(value).isNaN(), "ease($value) is NaN.")
        }})
    }
}

private val easings = listOf(
    named("Linear", Easing.Linear),
    named("SineIn", Easing.SineIn),
    named("SineOut", Easing.SineOut),
    named("SineInOut", Easing.SineInOut),
    named("QuadIn", Easing.QuadIn),
    named("QuadOut", Easing.QuadOut),
    named("QuadInOut", Easing.QuadInOut),
    named("CubicIn", Easing.CubicIn),
    named("CubicOut", Easing.CubicOut),
    named("CubicInOut", Easing.CubicInOut),
    named("QuartIn", Easing.QuartIn),
    named("QuartOut", Easing.QuartOut),
    named("QuartInOut", Easing.QuartInOut),
    named("QuintIn", Easing.QuintIn),
    named("QuintOut", Easing.QuintOut),
    named("QuintInOut", Easing.QuintInOut),
    named("ExpoIn", Easing.ExpoIn),
    named("ExpoOut", Easing.ExpoOut),
    named("ExpoInOut", Easing.ExpoInOut),
    named("CircIn", Easing.CircIn),
    named("CircOut", Easing.CircOut),
    named("CircInOut", Easing.CircInOut),
    named("BackIn", Easing.BackIn),
    named("BackOut", Easing.BackOut),
    named("BackInOut", Easing.BackInOut),
    named("ElasticIn", Easing.ElasticIn),
    named("ElasticOut", Easing.ElasticOut),
    named("ElasticInOut", Easing.ElasticInOut),
    named("BounceIn", Easing.BounceIn),
    named("BounceOut", Easing.BounceOut),
    named("BounceInOut", Easing.BounceInOut),
)

private fun testEasings(consumer: ThrowingConsumer<Easing>): Stream<DynamicTest> {
    return DynamicTest.stream(easings.iterator(), consumer)
}
