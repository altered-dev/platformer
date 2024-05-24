package me.altered.platformer.engine.node

import me.altered.platformer.engine.input.InputEvent
import me.altered.platformer.engine.util.Colors
import me.altered.platformer.engine.util.paint
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect
import kotlin.jvm.JvmStatic

open class Node(
    open val name: String,
    parent: Node? = null,
) {

    init {
        @Suppress("LeakingThis")
        parent?.addChild(this)
    }

    private var _parent: Node? = parent
    var parent: Node?
        get() = _parent
        set(value) {
            _parent?.removeChild(this)
            _parent = value
            value?.addChild(this)
        }

    private val _children = mutableSetOf<Node>()
    val children: Set<Node> by ::_children

    fun addChild(child: Node) {
        // TODO: cyclic tree check
        if (child === this) return
        child._parent?._children?.remove(child)
        child._parent = this
        _children += child
    }

    fun removeChild(child: Node) {
        if (child._parent !== this) return
        child._parent = null
        _children -= child
    }

    operator fun <N : Node> N.unaryPlus(): N {
        this@Node.addChild(this)
        return this
    }

    open val root: Node
        get() = findRoot()

    private tailrec fun findRoot(): Node {
        return (parent ?: return this).findRoot()
    }

    open fun debugDraw(canvas: Canvas, bounds: Rect) = Unit

    open fun ready() = Unit

    open fun update(delta: Float) = Unit

    open fun physicsUpdate(delta: Float) = Unit

    open fun draw(canvas: Canvas, bounds: Rect) = Unit

    open fun input(event: InputEvent) = Unit

    open fun destroy() = Unit

    override fun toString() = "[${this::class.simpleName}] $name"

    companion object {

        @JvmStatic
        protected val debugPaint = paint {
            mode = PaintMode.STROKE
            strokeWidth = 2.0f
            color4f = Colors.red
        }
    }
}
