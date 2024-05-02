package me.altered.platformer.util

sealed interface Either<out L, out R> {

    @JvmInline
    value class Left<out L>(val value: L) : Either<L, Nothing>

    @JvmInline
    value class Right<out R>(val value: R) : Either<Nothing, R>
}

fun <L> left(value: L) = Either.Left(value)

fun <R> right(value: R) = Either.Right(value)

val <T, L : T, R : T> Either<L, R>.value: T
    get() = when (this) {
        is Either.Left -> value
        is Either.Right -> value
    }

inline fun <T, L : T, R : T, TR> Either<L, R>.use(block: (T) -> TR): TR {
    return when (this) {
        is Either.Left -> block(value)
        is Either.Right -> block(value)
    }
}
