package me.altered.platformer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.encodeToHexString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import me.altered.platformer.editor.emptyBrush
import me.altered.platformer.editor.solid
import me.altered.platformer.level.data.Rectangle
import me.altered.platformer.timeline.Constant
import me.altered.platformer.timeline.const
import kotlin.test.Test

@OptIn(ExperimentalSerializationApi::class)
class SerializationTest {

    val module = SerializersModule {
        polymorphic(Any::class) {
            subclass(TestData::class)
        }
    }

    val cbor = Cbor {
        serializersModule = module
    }

    val json = Json {
//        useArrayPolymorphism = true
        prettyPrint = true

        serializersModule = module
    }

    private inline fun <reified T> encodeAndDecode(value: T) {
        println("value: $value")
        val cborEncoded = cbor.encodeToHexString(value)
        println("bytes: ${cbor.encodeToByteArray(value).size}")
        println("encoded: $cborEncoded")
        val cborDecoded = cbor.decodeFromHexString<T>(cborEncoded)
        println("decoded: $cborDecoded")
        val jsonEncoded = json.encodeToString(value)
        println("encoded: $jsonEncoded")
        val jsonDecoded = json.decodeFromString<T>(jsonEncoded)
        println("decoded: $jsonDecoded")
    }

    @Serializable
    @SerialName("test")
    data class TestData(val value: Float)

    @Test
    fun testExpression() {
        encodeAndDecode(Constant(100.0f))
        encodeAndDecode(const(TestData(100.0f)))
        encodeAndDecode(
            Rectangle(
                name = "wow",
                x = const(1.0f),
                y = const(2.0f),
                rotation = const(3.0f),
                width = const(4.0f),
                height = const(5.0f),
                cornerRadius = const(6.0f),
                fill = const(emptyBrush()),
                stroke = const(solid(0xFF000000)),
                strokeWidth = const(7.0f),
            )
        )
    }
}
