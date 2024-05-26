package me.altered.platformer.util

import me.altered.platformer.timeline.Keyframe

class KeyframeList<E>(
    // TODO: i really wish arrays and collections had common functions
    keyframes: Array<out Pair<Float, Keyframe<E>>>,
) {

    val first: Node<E>
    val last: Node<E>

    init {
        when (keyframes.size) {
            0 -> error("Keyframe list must not be empty.")
            1 -> {
                val (time, keyframe) = keyframes.single()
                first = Node(time, keyframe)
                last = first
            }
            else -> {
                // TODO: recognize keyframes with the same time
                // TODO: also probably make a contract that all incoming keyframes should be sorted
                val keyframes = keyframes.sortedBy { (t, _) -> t }
                var current: Node<E> = keyframes.first()
                    .let { (t, k) -> Node(t, k) }
                first = current
                for ((time, keyframe) in keyframes.drop(1)) {
                    val next = Node(time, keyframe, current)
                    current.next = next
                    current = next
                }
                last = current
            }
        }
    }

    tailrec fun find(time: Float, from: Node<E> = first): Node<E> {
        return if (from.next?.let { it.time >= time } == true) {
            find(time, from.next!!)
        } else {
            from
        }
    }

    override fun toString() = buildString {
        append('[')
        var current = first
        append(current)
        while (current.next != null) {
            append(", ")
            current = current.next!!
            append(current)
        }
        append(']')
    }

    class Node<E>(
        val time: Float,
        val keyframe: Keyframe<E>,
        var prev: Node<E>? = null,
        var next: Node<E>? = null,
    ) {

        fun shift(newTime: Float): Node<E> {
            var current = this
            while (current.prev?.let { it.time > newTime } == true) {
                current = current.prev!!
            }
            while (current.time < newTime && current.next != null) {
                current = current.next!!
            }
            return current
        }

        operator fun component1() = time
        operator fun component2() = keyframe

        override fun toString() = "${keyframe.value} at $time with ${keyframe.easing}"
    }
}
