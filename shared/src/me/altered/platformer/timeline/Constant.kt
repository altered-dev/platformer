package me.altered.platformer.timeline

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.jvm.JvmInline

/**
 * An expression defined by a number that never changes.
 */
@JvmInline
@Serializable(Constant.Serializer::class)
value class Constant<out T>(private val value: T) : Expression<T> {

    override fun eval(time: Float): T = value

    override fun toString() = value.toString()

    private class Serializer<T>(dataSerializer: KSerializer<T>) : KSerializer<Constant<T>> {

        @Serializable
        @SerialName("const")
        private data class Surrogate<T>(val value: T)

        private val serializer = Surrogate.serializer(dataSerializer)

        override val descriptor: SerialDescriptor = serializer.descriptor

        override fun serialize(encoder: Encoder, value: Constant<T>) {
            encoder.encodeSerializableValue(serializer, Surrogate(value.value))
        }

        override fun deserialize(decoder: Decoder): Constant<T> {
            return Constant(decoder.decodeSerializableValue(serializer).value)
        }
    }
}

/**
 * Creates an expression that is always resolved to [value].
 *
 * @param T the type of the expression.
 */
fun <T> const(value: T): Expression<T> = Constant(value)
