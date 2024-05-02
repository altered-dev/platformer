package me.altered.platformer.util

import kotlin.experimental.ExperimentalTypeInference

abstract class Event<F : Function<Unit>> {
    protected val subscribers = mutableSetOf<F>()

    operator fun plusAssign(subscriber: F) {
        if (subscribers.isEmpty()) {
            onSubscribe()
        }
        subscribers += subscriber
    }

    operator fun minusAssign(subscriber: F) {
        subscribers -= subscriber
        if (subscribers.isEmpty()) {
            onUnsubscribe()
        }
    }

    open fun onSubscribe() = Unit

    open fun onUnsubscribe() = Unit
}

open class Event0 : Event<() -> Unit>() {
    operator fun invoke() = subscribers.forEach { it() }
}

open class Event1<T1> : Event<(T1) -> Unit>() {
    operator fun invoke(arg1: T1) = subscribers.forEach { it(arg1) }
}

open class Event2<T1, T2> : Event<(T1, T2) -> Unit>() {
    operator fun invoke(arg1: T1, arg2: T2) = subscribers.forEach { it(arg1, arg2) }
}

open class Event3<T1, T2, T3> : Event<(T1, T2, T3) -> Unit>() {
    operator fun invoke(arg1: T1, arg2: T2, arg3: T3) = subscribers.forEach { it(arg1, arg2, arg3) }
}

open class Event4<T1, T2, T3, T4> : Event<(T1, T2, T3, T4) -> Unit>() {
    operator fun invoke(arg1: T1, arg2: T2, arg3: T3, arg4: T4) = subscribers.forEach { it(arg1, arg2, arg3, arg4) }
}

open class Event5<T1, T2, T3, T4, T5> : Event<(T1, T2, T3, T4, T5) -> Unit>() {
    operator fun invoke(arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5) = subscribers.forEach { it(arg1, arg2, arg3, arg4, arg5) }
}

open class Event6<T1, T2, T3, T4, T5, T6> : Event<(T1, T2, T3, T4, T5, T6) -> Unit>() {
    operator fun invoke(arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5, arg6: T6) = subscribers.forEach { it(arg1, arg2, arg3, arg4, arg5, arg6) }
}

inline fun event(crossinline onSubscribe: (block: Event0?) -> Unit) = object : Event0() {
    override fun onSubscribe() = onSubscribe(this)
    override fun onUnsubscribe() = onSubscribe(null)
}

inline fun <T1> event(crossinline onSubscribe: (block: Event1<T1>?) -> Unit) = object : Event1<T1>() {
    override fun onSubscribe() = onSubscribe(this)
    override fun onUnsubscribe() = onSubscribe(null)
}

inline fun <T1, T2> event(crossinline onSubscribe: (block: Event2<T1, T2>?) -> Unit) = object : Event2<T1, T2>() {
    override fun onSubscribe() = onSubscribe(this)
    override fun onUnsubscribe() = onSubscribe(null)
}

inline fun <T1, T2, T3> event(crossinline onSubscribe: (block: Event3<T1, T2, T3>?) -> Unit) = object : Event3<T1, T2, T3>() {
    override fun onSubscribe() = onSubscribe(this)
    override fun onUnsubscribe() = onSubscribe(null)
}

inline fun <T1, T2, T3, T4> event(crossinline onSubscribe: (block: Event4<T1, T2, T3, T4>?) -> Unit) = object : Event4<T1, T2, T3, T4>() {
    override fun onSubscribe() = onSubscribe(this)
    override fun onUnsubscribe() = onSubscribe(null)
}

inline fun <T1, T2, T3, T4, T5> event(crossinline onSubscribe: (block: Event5<T1, T2, T3, T4, T5>?) -> Unit) = object : Event5<T1, T2, T3, T4, T5>() {
    override fun onSubscribe() = onSubscribe(this)
    override fun onUnsubscribe() = onSubscribe(null)
}

inline fun <T1, T2, T3, T4, T5, T6> event(crossinline onSubscribe: (block: Event6<T1, T2, T3, T4, T5, T6>?) -> Unit) = object : Event6<T1, T2, T3, T4, T5, T6>() {
    override fun onSubscribe() = onSubscribe(this)
    override fun onUnsubscribe() = onSubscribe(null)
}

inline fun glfwEvent(crossinline block: (((Long) -> Unit)?) -> Any?) =
    event { e -> block(e?.let {{ e() }}) }

@OptIn(ExperimentalTypeInference::class)
inline fun <T1> glfwEvent(@BuilderInference crossinline block: (((Long, T1) -> Unit)?) -> Any?) =
    event<T1> { e -> block(e?.let {{ _, v1 -> e(v1) }}) }

@OptIn(ExperimentalTypeInference::class)
inline fun <T1, T2> glfwEvent(@BuilderInference crossinline block: (((Long, T1, T2) -> Unit)?) -> Any?) =
    event<T1, T2> { e -> block(e?.let {{ _, v1, v2 -> e(v1, v2) }}) }

@OptIn(ExperimentalTypeInference::class)
inline fun <T1, T2, T3> glfwEvent(@BuilderInference crossinline block: (((Long, T1, T2, T3) -> Unit)?) -> Any?) =
    event<T1, T2, T3> { e -> block(e?.let {{ _, v1, v2, v3 -> e(v1, v2, v3) }}) }

@OptIn(ExperimentalTypeInference::class)
inline fun <T1, T2, T3, T4> glfwEvent(@BuilderInference crossinline block: (((Long, T1, T2, T3, T4) -> Unit)?) -> Any?) =
    event<T1, T2, T3, T4> { e -> block(e?.let {{ _, v1, v2, v3, v4 -> e(v1, v2, v3, v4) }}) }

@OptIn(ExperimentalTypeInference::class)
inline fun <T1, T2, T3, T4, T5> glfwEvent(@BuilderInference crossinline block: (((Long, T1, T2, T3, T4, T5) -> Unit)?) -> Any?) =
    event<T1, T2, T3, T4, T5> { e -> block(e?.let {{ _, v1, v2, v3, v4, v5 -> e(v1, v2, v3, v4, v5) }}) }

@OptIn(ExperimentalTypeInference::class)
inline fun <T1, T2, T3, T4, T5, T6> glfwEvent(@BuilderInference crossinline block: (((Long, T1, T2, T3, T4, T5, T6) -> Unit)?) -> Any?) =
    event<T1, T2, T3, T4, T5, T6> { e -> block(e?.let {{ _, v1, v2, v3, v4, v5, v6 -> e(v1, v2, v3, v4, v5, v6) }}) }
