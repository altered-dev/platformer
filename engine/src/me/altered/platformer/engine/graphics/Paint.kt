package me.altered.platformer.engine.graphics

import org.jetbrains.skia.BlendMode
import org.jetbrains.skia.ColorFilter
import org.jetbrains.skia.ImageFilter
import org.jetbrains.skia.MaskFilter
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.PaintStrokeCap
import org.jetbrains.skia.PaintStrokeJoin
import org.jetbrains.skia.PathEffect
import org.jetbrains.skia.Shader
import kotlin.jvm.JvmInline

interface Paint {

    val nativePaint: org.jetbrains.skia.Paint

    fun copy(): Paint

    fun reset()

    var isAntiAlias: Boolean

    var isDither: Boolean

    var mode: PaintMode

    var color: Color

    var alpha: Int

    var strokeWidth: Float

    var strokeMiter: Float

    var strokeCap: PaintStrokeCap

    var strokeJoin: PaintStrokeJoin

    var shader: Shader?

    var colorFilter: ColorFilter?

    var blendMode: BlendMode

    var pathEffect: PathEffect?

    var maskFilter: MaskFilter?

    var imageFilter: ImageFilter?

    fun hasNothingToDraw(): Boolean
}

inline fun Paint(builder: Paint.() -> Unit): Paint = PaintDelegate(org.jetbrains.skia.Paint()).apply(builder)

@JvmInline
value class PaintDelegate(override val nativePaint: org.jetbrains.skia.Paint) : Paint {

    override fun copy(): Paint = PaintDelegate(nativePaint.makeClone())

    override fun reset() { nativePaint.reset() }

    override var isAntiAlias: Boolean
        get() = nativePaint.isAntiAlias
        set(value) { nativePaint.isAntiAlias = value }

    override var isDither: Boolean
        get() = nativePaint.isDither
        set(value) { nativePaint.isDither = value }

    override var mode: PaintMode
        get() = nativePaint.mode
        set(value) { nativePaint.mode = value }

    override var color: Color
        get() = Color(nativePaint.color)
        set(value) { nativePaint.color = value.value }

    override var alpha: Int
        get() = nativePaint.alpha
        set(value) { nativePaint.alpha = value }

    override var strokeWidth: Float
        get() = nativePaint.strokeWidth
        set(value) { nativePaint.strokeWidth = value }

    override var strokeMiter: Float
        get() = nativePaint.strokeMiter
        set(value) { nativePaint.strokeMiter = value }

    override var strokeCap: PaintStrokeCap
        get() = nativePaint.strokeCap
        set(value) { nativePaint.strokeCap = value }

    override var strokeJoin: PaintStrokeJoin
        get() = nativePaint.strokeJoin
        set(value) { nativePaint.strokeJoin = value }

    override var shader: Shader?
        get() = nativePaint.shader
        set(value) { nativePaint.shader = value }

    override var colorFilter: ColorFilter?
        get() = nativePaint.colorFilter
        set(value) { nativePaint.colorFilter = value }

    override var blendMode: BlendMode
        get() = nativePaint.blendMode
        set(value) { nativePaint.blendMode = value }

    override var pathEffect: PathEffect?
        get() = nativePaint.pathEffect
        set(value) { nativePaint.pathEffect = value }

    override var maskFilter: MaskFilter?
        get() = nativePaint.maskFilter
        set(value) { nativePaint.maskFilter = value }

    override var imageFilter: ImageFilter?
        get() = nativePaint.imageFilter
        set(value) { nativePaint.imageFilter = value }

    override fun hasNothingToDraw(): Boolean {
        return nativePaint.hasNothingToDraw()
    }
}
