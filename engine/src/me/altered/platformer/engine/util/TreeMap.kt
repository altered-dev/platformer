package me.altered.platformer.engine.util

import kotlin.jvm.Transient

/**
 * TODO: implement removing
 */
class TreeMap<K : Comparable<K>, V>() : AbstractMutableMap<K, V>() {

    override val entries = EntrySet()

    override var size = 0
        private set

    @Transient
    private var root: Entry<K, V>? = null

    val firstEntry: MutableMap.MutableEntry<K, V>?
        get() {
            var entry = root
            while (entry?.left != null) {
                entry = entry.left
            }
            return entry
        }

    val lastEntry: MutableMap.MutableEntry<K, V>?
        get() {
            var entry = root
            while (entry?.right != null) {
                entry = entry.right
            }
            return entry
        }

    constructor(from: Map<K, V>) : this() {
        from.forEach { put(it.key, it.value) }
    }

    override fun put(key: K, value: V): V? {
        var t: Entry<K, V>? = root ?: return addToEmpty(key, value)
        var cmp: Int
        var parent: Entry<K, V>
        do {
            parent = t!!
            cmp = key.compareTo(t.key)
            if (cmp < 0) {
                t = t.left
            } else if (cmp == 0) {
                val oldValue = t.value
                t.value = value
                return oldValue
            } else {
                t = t.right
            }
        } while (t != null)
        addEntry(key, value, parent, cmp < 0)
        return null
    }

    override fun get(key: K): V? {
        return getEntry(key)?.value
    }

    fun single(): MutableMap.MutableEntry<K, V> {
        return root ?: throw NoSuchElementException()
    }

    fun ceilingEntry(key: K): MutableMap.MutableEntry<K, V>? {
        var entry = this.root

        while (entry != null) {
            val cmp = key.compareTo(entry.key)
            when {
                cmp < 0 -> entry = entry.left ?: return entry
                cmp <= 0 -> return entry
                else -> {
                    if (entry.right == null) {
                        var parent = entry.parent
                        var ch: Entry<K, V> = entry
                        while (parent != null && ch === parent.right) {
                            ch = parent
                            parent = parent.parent
                        }
                        return parent
                    }

                    entry = entry.right
                }
            }
        }

        return null
    }

    fun floorEntry(key: K): MutableMap.MutableEntry<K, V>? {
        var entry = this.root

        while (entry != null) {
            val cmp = key.compareTo(entry.key)
            when {
                cmp > 0 -> entry = entry.right ?: return entry
                cmp >= 0 -> return entry
                else -> {
                    if (entry.left == null) {
                        var parent = entry.parent
                        var ch: Entry<K, V> = entry
                        while (parent != null && ch === parent.left) {
                            ch = parent
                            parent = parent.parent
                        }
                        return parent
                    }

                    entry = entry.left
                }
            }
        }

        return null
    }

    private fun addToEmpty(key: K, value: V): Nothing? {
        root = Entry(key, value, null)
        size = 1
        return null
    }

    private fun addEntry(key: K, value: V, parent: Entry<K, V>, addToLeft: Boolean) {
        val entry = Entry(key, value, parent)
        if (addToLeft) {
            parent.left = entry
        } else {
            parent.right = entry
        }
        fixAfterInsertion(entry)
        size++
    }

    private fun fixAfterInsertion(entry: Entry<K, V>) {
        var x: Entry<K, V>? = entry
        x!!.color = false

        while (x != null && x !== this.root && !x.parent!!.color) {
            var y: Entry<K, V>?
            if (x.parent === x.parent?.parent?.left) {
                y = x.parent?.parent?.right
                if (!y.color) {
                    x.parent.color = true
                    y.color = true
                    x.parent.parent.color = false
                    x = x.parent.parent
                } else {
                    if (x === x.parent.right) {
                        x = x.parent
                        this.rotateLeft(x)
                    }

                    x.parent.color = true
                    x.parent.parent.color = false
                    this.rotateRight(x.parent.parent)
                }
            } else {
                y = x.parent.parent.left
                if (!y.color) {
                    x.parent.color = true
                    y.color = true
                    x.parent.parent.color = false
                    x = x.parent.parent
                } else {
                    if (x === x.parent.left) {
                        x = x.parent
                        this.rotateRight(x)
                    }

                    x.parent.color = true
                    x.parent.parent.color = false
                    this.rotateLeft(x.parent.parent)
                }
            }
        }

        root!!.color = true
    }

    private fun rotateLeft(p: Entry<K, V>?) {
        if (p == null) return
        val r = p.right
        p.right = r!!.left

        r.left?.parent = p
        r.parent = p.parent

        when {
            p.parent == null -> this.root = r
            p.parent!!.left === p -> p.parent!!.left = r
            else -> p.parent!!.right = r
        }

        r.left = p
        p.parent = r
    }

    private fun rotateRight(p: Entry<K, V>?) {
        if (p == null) return
        val l = p.left
        p.left = l!!.right

        l.right?.parent = p
        l.parent = p.parent

        when {
            p.parent == null -> this.root = l
            p.parent!!.right === p -> p.parent!!.right = l
            else -> p.parent!!.left = l
        }

        l.right = p
        p.parent = l
    }

    private fun getEntry(key: K): Entry<K, V>? {
        var entry = root
        while (entry != null) {
            val cmp = key.compareTo(entry.key)
            when {
                cmp < 0 -> entry = entry.left
                cmp == 0 -> return entry
                cmp > 0 -> entry = entry.right
            }
        }
        return null
    }

    private class Entry<K, V>(
        override var key: K,
        override var value: V,
        var parent: Entry<K, V>?,
    ) : MutableMap.MutableEntry<K, V> {
        var left: Entry<K, V>? = null
        var right: Entry<K, V>? = null
        var color: Boolean = true

        override fun setValue(newValue: V): V {
            val oldValue = this.value
            this.value = newValue
            return oldValue
        }

        override fun equals(other: Any?): Boolean {
            return other is Map.Entry<*, *> &&
                this.key == other.key &&
                this.value == other.value
        }

        override fun hashCode(): Int {
            return key.hashCode() xor value.hashCode()
        }

        override fun toString(): String {
            return key.toString() + "=" + this.value
        }
    }

    inner class EntrySet : AbstractMutableSet<MutableMap.MutableEntry<K, V>>() {
        override val size: Int
            get() = TODO("Not yet implemented")

        override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>> {
            TODO("Not yet implemented")
        }

        override fun add(element: MutableMap.MutableEntry<K, V>): Boolean {
            TODO("Not yet implemented")
        }
    }

    companion object {
        private var <K, V> Entry<K, V>?.color: Boolean
            get() = this?.color ?: true
            set(value) { this?.color = value }

        private val <K, V> Entry<K, V>?.parent: Entry<K, V>?
            get() = this?.parent

        private val <K, V> Entry<K, V>?.left: Entry<K, V>?
            get() = this?.left

        private val <K, V> Entry<K, V>?.right: Entry<K, V>?
            get() = this?.right
    }
}
