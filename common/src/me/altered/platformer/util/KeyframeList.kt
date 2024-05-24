package me.altered.platformer.util

import me.altered.platformer.timeline.Keyframe

class KeyframeList<E>(keyframes: Array<out Pair<Float, Keyframe<E>>>) {

    val first: Node<E>
    val last: Node<E>

    init {
        when (keyframes.size) {
            0 -> error("Keyframe list must not be empty.")
            1 -> {
                val (time, keyframe) = keyframes.single()
                first = Node(null, null, time, keyframe)
                last = first
            }
            else -> {
                // TODO: recognize keyframes with the same time
                // TODO: also probably make a contract that all incoming keyframes should be sorted
                val keyframes = keyframes.sortedBy { (t, _) -> t }
                var current: Node<E> = keyframes.first()
                    .let { (t, k) -> Node(null, null, t, k) }
                first = current
                for ((time, keyframe) in keyframes.drop(1)) {
                    val next = Node(current, null, time, keyframe)
                    current.next = next
                    current = next
                }
                last = current
            }
        }
    }

    fun find(time: Float): Node<E> {
        var current = first
        while (current.next?.let { it.time >= time } == true) {
            current = current.next!!
        }
        return current
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
        var prev: Node<E>?,
        var next: Node<E>?,
        val time: Float,
        val keyframe: Keyframe<E>,
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

        override fun toString() = "$keyframe at $time"
    }
}
