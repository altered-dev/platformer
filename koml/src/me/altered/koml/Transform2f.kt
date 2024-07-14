package me.altered.koml

open class Transform2f(
    override var transX: Float,
    override var transY: Float,
    override var scaleX: Float,
    override var scaleY: Float,
    override var skewX: Float,
    override var skewY: Float,
) : Transform2fc {

    

    override fun toString() = "[tx=$transX, ty=$transY, sx=$scaleX, sy=$scaleY, wx=$skewX, wy=$skewY]"
}